package com.example.lvtn.service;

import com.example.lvtn.common.CommonUtil;
import com.example.lvtn.common.JwtUtil;
import com.example.lvtn.dto.*;
import com.example.lvtn.model.THCS;
import com.example.lvtn.model.THPT;
import com.example.lvtn.model.TaiKhoan;
import com.example.lvtn.repository.HocSinhRepository;
import com.example.lvtn.repository.THCSRepository;
import com.example.lvtn.repository.THPTRepository;
import com.example.lvtn.repository.TaiKhoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaiKhoanService {
    private final TaiKhoanRepository tkRepository;
    private final JwtUtil jwtUtil;
    private final THCSRepository thcsRepository;
    private final THPTRepository thptRepository;
    private final HocSinhRepository hsRepository;
    private final CommonUtil commonUtil;
    private final PasswordEncoder passwordEncoder;

    public TaiKhoanService(TaiKhoanRepository tkRepository, JwtUtil jwtUtil, THPTRepository thptRepository,
                           THCSRepository thcsRepository, HocSinhRepository hsRepository, CommonUtil commonUtil, PasswordEncoder passwordEncoder) {
        this.tkRepository = tkRepository;
        this.jwtUtil = jwtUtil;
        this.thptRepository = thptRepository;
        this.thcsRepository = thcsRepository;
        this.hsRepository = hsRepository;
        this.commonUtil = commonUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String getTenDinhDanh(int maLoaiTK, String soDinhDanh) {
        return switch (maLoaiTK) {
            case 0 -> "Admin";
            case 1 -> "Sở GD&ĐT TP Cần Thơ";
            case 2 -> thcsRepository.findById(soDinhDanh)
                    .map(THCS::getTenTHCS)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy trường THCS với mã " + soDinhDanh));
            case 3 -> thptRepository.findById(soDinhDanh)
                    .map(THPT::getTenTHPT)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy trường THPT với mã " + soDinhDanh));
            case 4 -> hsRepository.findById(soDinhDanh)
                    .map(hs -> hs.getHoVaChuLotHS() + " " + hs.getTenHS())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy học sinh với mã " + soDinhDanh));
            default -> throw new AccessDeniedException("Loại tài khoản không hợp lệ!");
        };
    }

    @Transactional
    public TaiKhoanToken login(TaiKhoanDangNhap tkDangNhap) {
        TaiKhoan tk = tkRepository.findById(tkDangNhap.getTenTK())
                .orElseThrow(() -> new EntityNotFoundException("Tên tài khoản hoặc mật khẩu không đúng!"));
        String rawPassword = tkDangNhap.getMatKhau();
        boolean isDefaultPassword = tk.getMatKhau() != null;
        if (isDefaultPassword) {
            if (!tk.getMatKhau().equals(rawPassword)) {
                throw new EntityNotFoundException("Tên tài khoản hoặc mật khẩu không đúng!");
            }
        } else {
            if (!passwordEncoder.matches(rawPassword, tk.getHashMatKhau())) {
                throw new EntityNotFoundException("Tên tài khoản hoặc mật khẩu không đúng!");
            }
        }
        String token = jwtUtil.generateToken(tk);
        int maLoaiTK = tk.getMaLoaiTK();
        String tenDinhDanh = getTenDinhDanh(maLoaiTK, tk.getSoDinhDanh());
        TaiKhoanDTO tkDTO = new TaiKhoanDTO(tk.getTenTK(), tk.getMaLoaiTK(), tk.getSoDinhDanh(), tenDinhDanh);
        return new TaiKhoanToken(token, tkDTO);
    }

    @Transactional
    public TaiKhoanDTO taoTaiKhoan(TaiKhoanRequest tkRequest) {
        String newPass = commonUtil.generateRandomPassword(8);
        String hashedPass = passwordEncoder.encode(newPass);
        TaiKhoan tk = new TaiKhoan(tkRequest.getTenTK(), newPass, tkRequest.getMaLoaiTK(), tkRequest.getSoDinhDanh(), hashedPass, false);
        tkRepository.save(tk);
        return new TaiKhoanDTO(tkRequest.getTenTK(), tkRequest.getMaLoaiTK(), tkRequest.getSoDinhDanh(), getTenDinhDanh(tkRequest.getMaLoaiTK(), tkRequest.getSoDinhDanh()));
    }

    @Transactional
    public void deleteTaiKhoan(String tenTK) {
        if (!tkRepository.existsById(tenTK)) {
            throw new EntityNotFoundException("Không tìm thấy tài khoản: " + tenTK);
        }
        tkRepository.deleteById(tenTK);
    }

    @Transactional
    public void changePassword(String tenTK, ChangePasswordDTO changePasswordDTO) {
        TaiKhoan taiKhoan = tkRepository.findById(tenTK)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản: " + tenTK));
        String oldPassword = changePasswordDTO.getOldPassword();
        String newPassword = changePasswordDTO.getNewPassword();
        String confirmPassword = changePasswordDTO.getConfirmNewPassword();
        boolean isDefaultPassword = taiKhoan.getMatKhau() != null;
        if (isDefaultPassword) {
            if (!taiKhoan.getMatKhau().equals(oldPassword)) {
                throw new IllegalArgumentException("Mật khẩu cũ không đúng!");
            }
        } else {
            if (!passwordEncoder.matches(oldPassword, taiKhoan.getHashMatKhau())) {
                throw new IllegalArgumentException("Mật khẩu cũ không đúng!");
            }
        }
        // Kiểm tra mật khẩu mới có khác mật khẩu cũ không
        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("Mật khẩu mới phải khác mật khẩu cũ!");
        }
        // Kiểm tra xác nhận mật khẩu mới có trùng khớp với mật khẩu mới không
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Xác nhận mật khẩu mới phải trùng khớp với mật khẩu mới đã nhập!");
        }
        // Cập nhật mật khẩu mới
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        taiKhoan.setHashMatKhau(hashedNewPassword);
        if (isDefaultPassword) {
            taiKhoan.setMatKhau(null);
            taiKhoan.setDaDoiMatKhau(true);
        }
        tkRepository.save(taiKhoan);
    }

    @Transactional
    public List<TaiKhoanDTO> findAllTaiKhoan() {
        List<TaiKhoan> taiKhoan = tkRepository.findAllTaiKhoan();
        List<TaiKhoanDTO> taiKhoanDTO = new ArrayList<>();
        for (TaiKhoan tk : taiKhoan) {
            taiKhoanDTO.add(new TaiKhoanDTO(tk.getTenTK(), tk.getMaLoaiTK(), tk.getSoDinhDanh(), getTenDinhDanh(tk.getMaLoaiTK(), tk.getSoDinhDanh())));
        }
        return taiKhoanDTO;
    }

    @Transactional
    public List<TaiKhoanDTO> findTaiKhoanBykeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }
        List<Object[]> obj = tkRepository.findTKBykeyword(keyword);
        List<TaiKhoanDTO> result = new ArrayList<>();
        for (Object[] row : obj) {
            String tenTK = (String) row[0];
            int maLoaiTK = ((Number) row[1]).intValue();
            String soDinhDanh = (String) row[2];
            String tenDinhDanh = (String) row[3];
            result.add(new TaiKhoanDTO(tenTK, maLoaiTK, soDinhDanh, tenDinhDanh));
        }
        return result;
    }

    @Transactional
    public byte[] exportTaiKhoanToExcel() throws IOException {
        List<TaiKhoanDTO> tkList = findAllTaiKhoan();
        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);
            Sheet sheet = workbook.createSheet("TaiKhoan");
            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Tên tài khoản", "Mã loại TK", "Số định danh", "Tên định danh" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            // Data rows
            int rowIdx = 1;
            for (TaiKhoanDTO dto : tkList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getTenTK());
                row.createCell(1).setCellValue(dto.getMaLoaiTK());
                row.createCell(2).setCellValue(dto.getSoDinhDanh());
                row.createCell(3).setCellValue(dto.getTenDinhDanh());
            }
            // Auto-size column
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Xuất ra ByteArrayInputStream để trả về API
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
            return out.toByteArray();
        }
    }

    @Transactional
    public byte[] exportTaiKhoanGVToExcel() throws IOException {
        List<TaiKhoan> taiKhoan = tkRepository.findTaiKhoanGV();
        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);
            Sheet sheet = workbook.createSheet("TaiKhoanGV");
            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Tên tài khoản", "Mật khẩu mặc định", "Mã loại TK", "Số định danh", "Tên định danh" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            // Data rows
            int rowIdx = 1;
            for (TaiKhoan tk : taiKhoan) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(tk.getTenTK());
                row.createCell(1).setCellValue(tk.getMatKhau());
                row.createCell(2).setCellValue(tk.getMaLoaiTK());
                row.createCell(3).setCellValue(tk.getSoDinhDanh());
                row.createCell(4).setCellValue(getTenDinhDanh(tk.getMaLoaiTK(), tk.getSoDinhDanh()));
            }
            // Auto-size column
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Xuất ra ByteArrayInputStream để trả về API
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
            return out.toByteArray();
        }
    }


    @Transactional
    public ImportAccountDTO importAccountsFromExcel(MultipartFile file) {
        ImportAccountDTO result = new ImportAccountDTO();
        // 1. Đọc Excel (Apache POI/EasyExcel)
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            // 2. Map mỗi row -> TaiKhoanDTO
            // Bỏ qua dòng đầu tiên (tiêu đề)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String tenTK = row.getCell(0).getStringCellValue().trim();
                    int maLoaiTK = (int) row.getCell(1).getNumericCellValue();
                    String soDinhDanh = row.getCell(2).getStringCellValue().trim();

                    // Validate dữ liệu cơ bản
                    if (tenTK.isEmpty() || maLoaiTK <= 0) {
                        throw new IllegalArgumentException("Dòng " + (i+1) + " thiếu thông tin bắt buộc");
                    }
                    if (tkRepository.existsById(tenTK)) {
                        throw new IllegalArgumentException("Tài khoản " + tenTK + " đã tồn tại trong danh sách");
                    }
                    String newPass = commonUtil.generateRandomPassword(8);
                    String hashedPass = passwordEncoder.encode(newPass);
                    TaiKhoan taiKhoan = new TaiKhoan(tenTK, newPass, maLoaiTK, soDinhDanh, hashedPass, false);
                    // 3. Lưu vào DB
                    tkRepository.save(taiKhoan);

                    // Map sang DTO để trả về
                    TaiKhoanDTO dto = new TaiKhoanDTO(tenTK, maLoaiTK, soDinhDanh, getTenDinhDanh(maLoaiTK, soDinhDanh));
                    result.getSuccessList().add(dto);
                } catch (Exception ex) {
                    // Ghi nhận lỗi cho từng dòng
                    result.getErrorList().add("Dòng " + (i+1) + ": " + ex.getMessage());
                }
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("File Excel không hợp lệ hoặc bị hỏng", e);
        }

        // 4. Trả về danh sách tài khoản đã tạo
        return result;
    }

    @Transactional
    public ResetPasswordDTO resetPassword(String tenTK) {
        TaiKhoan tk = tkRepository.findById(tenTK)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản: " + tenTK));
        if (tk.getMaLoaiTK() == 0) throw new IllegalArgumentException("Không được phép đặt lại mật khẩu cho tài khoản này!");
        String newPass = commonUtil.generateRandomPassword(8);
        String hashedPass = passwordEncoder.encode(newPass);
        tk.setMatKhau(newPass);
        tk.setHashMatKhau(hashedPass);
        tk.setDaDoiMatKhau(false);
        tkRepository.save(tk);
        return new ResetPasswordDTO(tk.getTenTK(), newPass);
    }
}
