package com.example.lvtn.service;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.example.lvtn.common.CommonUtil;
import com.example.lvtn.dto.*;
import com.example.lvtn.model.*;
import com.example.lvtn.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class HocSinhService {

    private final HocSinhRepository hsRepository;
    private final KQHocTapRepository kqhtRepository;
    private final NguyenVongRepository nvRepository;
    private final TaiKhoanRepository tkRepository;
    private final CommonUtil commonUtil;
    private final ThiSinhRepository tsRepository;
    private final THCSRepository thcsRepository;
    private final THPTRepository thptRepository;
    private final String uploadDir;
    private final PasswordEncoder passwordEncoder;

    public HocSinhService(HocSinhRepository hsRepository, KQHocTapRepository kqhtRepository, NguyenVongRepository nvRepository,
                          TaiKhoanRepository tkRepository, CommonUtil commonUtil, ThiSinhRepository tsRepository, THCSRepository thcsRepository, THPTRepository thptRepository,
                          @Value("${app.upload.dir}") String uploadDir, PasswordEncoder passwordEncoder) {
        this.hsRepository = hsRepository;
        this.kqhtRepository = kqhtRepository;
        this.nvRepository = nvRepository;
        this.tkRepository = tkRepository;
        this.commonUtil = commonUtil;
        this.tsRepository = tsRepository;
        this.thcsRepository = thcsRepository;
        this.thptRepository = thptRepository;
        this.uploadDir = uploadDir;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public List<HocSinhDTO> toListHocSinhDTO(List<HocSinhFullData> hs) {
        List<HocSinhDTO> list_hsDTO = new ArrayList<>();
        for (HocSinhFullData h : hs) {
            HocSinhDTO hsDTO = new HocSinhDTO();
            String maHS = h.getMaHS();
            hsDTO.setThongTinHS(h);
            hsDTO.setKqHocTap(kqhtRepository.findById_MaHS(maHS));
            hsDTO.setNguyenVong(nvRepository.findNguyenVongByMaHS(maHS));
            list_hsDTO.add(hsDTO);
        }
        return list_hsDTO;
    }

    @Transactional
    private String saveAnhDaiDien(String maHS, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("File không có tên hợp lệ hoặc thiếu phần mở rộng!");
        }

        // Kiểm tra phần mở rộng
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!fileExt.equals(".jpg") && !fileExt.equals(".jpeg") && !fileExt.equals(".png")) {
            throw new IllegalArgumentException("Chỉ chấp nhận tệp hình ảnh (jpg, jpeg, png)!");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        String fileName = maHS + ".jpg";
        Path filePath = uploadPath.resolve(fileName);

        BufferedImage original = ImageIO.read(file.getInputStream());
        if (original == null) {
            throw new IllegalArgumentException("Không thể đọc dữ liệu hình ảnh!");
        }

        int width = original.getWidth();
        int height = original.getHeight();

        // Tính toán crop để giữ tỷ lệ 3:4
        double targetRatio = 3.0 / 4.0;
        int newWidth = width;
        int newHeight = (int) (width / targetRatio);
        if (newHeight > height) {
            newHeight = height;
            newWidth = (int) (height * targetRatio);
        }

        // Crop chính giữa
        int x = (width - newWidth) / 2;
        int y = (height - newHeight) / 2;
        BufferedImage cropped = original.getSubimage(x, y, newWidth, newHeight);

        // Lưu lại ảnh mới
        try (OutputStream out = Files.newOutputStream(filePath)) {
            ImageIO.write(cropped, "jpg", out);
        }

        return fileName;
    }

    /*
     * Các hàm tra cứu thông tin học sinh
     */

    @Transactional(readOnly = true)
    public HocSinhDTO findHSByMaHS(String maHS) {
        HocSinhFullData hs = hsRepository.findHocSinhByMaHS(maHS).orElseThrow(() -> new EntityNotFoundException("Học sinh có mã số " + maHS + " không tồn tại!"));
        List<KQHocTap> kqht = kqhtRepository.findById_MaHS(maHS);
        List<NguyenVongFullData> nv = nvRepository.findNguyenVongByMaHS(maHS);
        return new HocSinhDTO(hs, kqht, nv);
    }

    @Transactional(readOnly = true)
    public List<HocSinhDTO> findHSByMaTHCS (String maTHCS){
        List<HocSinhFullData> lhs = hsRepository.findHocSinhByMaTHCS(maTHCS);
        return toListHocSinhDTO(lhs);
    }

    @Transactional(readOnly = true)
    public List<HocSinhDTO> findHSByMaTHCSAndKeyword(String maTHCS, String keyword) {
        List<HocSinhFullData> lhs = hsRepository.findHocSinhByMaTHCSAndKeyword(maTHCS, keyword);
        return toListHocSinhDTO(lhs);
    }

    @Transactional(readOnly = true)
    public List<HocSinhDTO> findHSNgoaiTinhBymaTHPT (String maTHPT){
        List<HocSinhFullData> lhs = hsRepository.findHocSinhNgoaiTinhByMaTHPT(maTHPT);
        return toListHocSinhDTO(lhs);
    }

    @Transactional(readOnly = true)
    public List<HocSinhDTO> findHSNgoaiTinhByMaTHPTAndKeyword(String maTHPT, String keyword) {
        List<HocSinhFullData> lhs = hsRepository.findHocSinhNgoaiTinhByMaTHPTAndKeyword(maTHPT, keyword);
        return toListHocSinhDTO(lhs);
    }

    @Transactional(readOnly = true)
    public byte[] exportHocSinhTHCSToExcel(String maTHCS) throws IOException {
        List<HocSinhDTO> lhs = findHSByMaTHCS(maTHCS);
        return exportHocSinhToExcel(lhs);
    }

    @Transactional(readOnly = true)
    public byte[] exportHocSinhNgoaiTinhTHPTToExcel(String maTHPT) throws IOException {
        List<HocSinhDTO> lhs = findHSNgoaiTinhBymaTHPT(maTHPT);
        return exportHocSinhToExcel(lhs);
    }

    @Transactional(readOnly = true)
    public byte[] exportHocSinhToExcel(List<HocSinhDTO> lhs) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (Workbook workbook = new XSSFWorkbook()) {
            // --- Tạo style cho hàng header ---
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);
            // Bảng ExportHocSinh
            Sheet sheetHS = workbook.createSheet("ExportHocSinh");
            String[] headersHS = {
                    "Mã học sinh", "Họ và chữ lót", "Tên", "Nữ", "Ngày sinh", "Nơi sinh", "Mã dân tộc", "Địa chỉ thường trú",
                    "Chỗ ở hiện nay", "Mã ĐTƯT", "Mã ĐTKK", "Mã ĐTKK chuyên", "Ngoại ngữ đang học", "Ngoại ngữ dự thi", "Số điện thoại",
                     "ĐTB môn chuyên", "Tổng ĐTB lớp 9", "Tên THCS (ngoài TPCT)", "Tên xã (ngoài TPCT)", "Tên tỉnh (ngoài TPCT)"
            };
            Row headerHS = sheetHS.createRow(0);
            for (int i = 0; i < headersHS.length; i++) {
                Cell cell = headerHS.createCell(i);
                cell.setCellValue(headersHS[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowHS = 1;
            for (HocSinhDTO dto : lhs) {
                Row row = sheetHS.createRow(rowHS++);
                var hs = dto.getThongTinHS();
                // Lấy thông tin học sinh
                row.createCell(0).setCellValue(hs.getMaHS());
                row.createCell(1).setCellValue(hs.getHoVaChuLotHS());
                row.createCell(2).setCellValue(hs.getTenHS());
                row.createCell(3).setCellValue(commonUtil.convertBooleanToString(hs.getGioiTinh()));
                LocalDate ngaySinh = hs.getNgaySinh();
                row.createCell(4).setCellValue(ngaySinh != null ? ngaySinh.format(formatter) : "");
                row.createCell(5).setCellValue(hs.getNoiSinh());
                row.createCell(6).setCellValue(hs.getMaDT());
                row.createCell(7).setCellValue(hs.getDiaChiThuongTru());
                row.createCell(8).setCellValue(hs.getChoOHienNay());
                row.createCell(9).setCellValue(hs.getMaDTUT());
                row.createCell(10).setCellValue(hs.getMaDTKK());
                row.createCell(11).setCellValue(hs.getMaDTKKChuyen());
                row.createCell(12).setCellValue(hs.getNgoaiNguDangHoc());
                row.createCell(13).setCellValue(hs.getNgoaiNguDuThi());
                row.createCell(14).setCellValue(hs.getSoDienThoai());
                commonUtil.setCellValueAsDecimal(row, 15, hs.getDiemTBMonChuyen(), workbook);
                commonUtil.setCellValueAsDecimal(row, 16, hs.getTongDiemTBLop9(), workbook);
                row.createCell(17).setCellValue(hs.getTenTHCSNgoaiTPCT());
                row.createCell(18).setCellValue(hs.getTenXaNgoaiTPCT());
                row.createCell(19).setCellValue(hs.getTenTinhNgoaiTPCT());
            }
            // Bảng ExportKQHocTap
            Sheet sheetKQHT = workbook.createSheet("ExportKQHocTap");
            String[] headersKQHT = {"Mã học sinh", "Lớp", "Học tập", "Rèn luyện"};
            Row headerKQHT = sheetKQHT.createRow(0);
            for (int i = 0; i < headersKQHT.length; i++) {
                Cell cell = headerKQHT.createCell(i);
                cell.setCellValue(headersKQHT[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowKQHT = 1;
            for (HocSinhDTO dto : lhs) {
                for (KQHocTap kq : dto.getKqHocTap()) {
                    Row row = sheetKQHT.createRow(rowKQHT++);
                    row.createCell(0).setCellValue(dto.getThongTinHS().getMaHS());
                    row.createCell(1).setCellValue(kq.getId().getLop());
                    row.createCell(2).setCellValue(kq.getHocTap());
                    row.createCell(3).setCellValue(kq.getRenLuyen());
                }
            }
            // Bảng ExportNguyenVong
            Sheet sheetNV = workbook.createSheet("ExportNguyenVong");
            String[] headersNV = {"Mã học sinh", "Thứ tự nguyện vọng", "Mã THPT", "NV 2B", "Lớp tiếng Pháp", "Mã lớp chuyên"};
            Row headerNV = sheetNV.createRow(0);
            for (int i = 0; i < headersNV.length; i++) {
                Cell cell = headerNV.createCell(i);
                cell.setCellValue(headersNV[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowNV = 1;
            for (HocSinhDTO dto : lhs) {
                for (NguyenVongFullData nv : dto.getNguyenVong()) {
                    Row row = sheetNV.createRow(rowNV++);
                    row.createCell(0).setCellValue(dto.getThongTinHS().getMaHS());
                    row.createCell(1).setCellValue(nv.getThuTu());
                    row.createCell(2).setCellValue(nv.getMaTHPT());
                    row.createCell(3).setCellValue(commonUtil.convertBooleanToString(nv.getNv2B()));
                    row.createCell(4).setCellValue(commonUtil.convertBooleanToString(nv.getLopTiengPhap()));
                    row.createCell(5).setCellValue(nv.getMaLopChuyen() != null ? nv.getMaLopChuyen() : "");
                }
            }
            // Auto-size các cột trong các bảng
            for (Sheet s : new Sheet[]{sheetHS, sheetKQHT, sheetNV}) {
                Row header = s.getRow(0);
                if (header != null) {
                    for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
                        s.autoSizeColumn(i);
                    }
                }
            }
            // Xuất ra ByteArrayInputStream để trả về API
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
            return out.toByteArray();
        }
    }

    @Transactional(readOnly = true)
    public byte[] exportTKHocSinhTHCSToExcel(String maTHCS) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<TaiKhoanHSTHCS> ltk = hsRepository.findTKHocSinhByMaTHCS(maTHCS);
        try (Workbook workbook = new XSSFWorkbook()) {
            // --- Tạo style cho hàng header ---
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);
            // Bảng ExportHocSinh
            Sheet sheet = workbook.createSheet("ExportTKHocSinh");
            String[] headers = {"Tên tài khoản", "Mật khẩu mặc định", "Họ và chữ lót", "Tên", "Nữ", "Ngày sinh", "Nơi sinh"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowHS = 1;
            for (TaiKhoanHSTHCS tk : ltk) {
                Row row = sheet.createRow(rowHS++);
                // Lấy thông tin học sinh
                row.createCell(0).setCellValue(tk.getTenTK());
                row.createCell(1).setCellValue(tk.getMatKhau());
                row.createCell(2).setCellValue(tk.getHoVaChuLotHS());
                row.createCell(3).setCellValue(tk.getTenHS());
                row.createCell(4).setCellValue(commonUtil.convertBooleanToString(tk.getGioiTinh()));
                LocalDate ngaySinh = tk.getNgaySinh();
                row.createCell(5).setCellValue(ngaySinh != null ? ngaySinh.format(formatter) : "");
                row.createCell(6).setCellValue(tk.getNoiSinh());
            }
            // Auto-size các cột trong các bảng
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

    @Transactional(readOnly = true)
    public byte[] exportTKHocSinhNgoaiTinhToExcel(String maTHPT) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<TaiKhoanHSNgoaiTinh> ltk = hsRepository.findTKHocSinhNgoaiTinhByMaTHPT(maTHPT);
        try (Workbook workbook = new XSSFWorkbook()) {
            // --- Tạo style cho hàng header ---
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);
            // Bảng ExportHocSinh
            Sheet sheet = workbook.createSheet("ExportTKHocSinh");
            String[] headers = {
                    "Tên tài khoản", "Mật khẩu mặc định", "Họ và chữ lót", "Tên", "Nữ", "Ngày sinh", "Nơi sinh",
                    "Trường THCS", "Xã/Phường/Đặc khu", "Tỉnh/Thành phố"
            };
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowHS = 1;
            for (TaiKhoanHSNgoaiTinh tk : ltk) {
                Row row = sheet.createRow(rowHS++);
                // Lấy thông tin học sinh
                row.createCell(0).setCellValue(tk.getTenTK());
                row.createCell(1).setCellValue(tk.getMatKhau());
                row.createCell(2).setCellValue(tk.getHoVaChuLotHS());
                row.createCell(3).setCellValue(tk.getTenHS());
                row.createCell(4).setCellValue(commonUtil.convertBooleanToString(tk.getGioiTinh()));
                LocalDate ngaySinh = tk.getNgaySinh();
                row.createCell(5).setCellValue(ngaySinh != null ? ngaySinh.format(formatter) : "");
                row.createCell(6).setCellValue(tk.getNoiSinh());
                row.createCell(7).setCellValue(tk.getTenTHCSNgoaiTPCT());
                row.createCell(8).setCellValue(tk.getTenXaNgoaiTPCT());
                row.createCell(9).setCellValue(tk.getTenTinhNgoaiTPCT());
            }
            // Auto-size các cột trong các bảng
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

    private String lowerFirst(String value) {
        if (value == null || value.isEmpty()) return "";
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    @Transactional(readOnly = true)
    public byte[] exportHocSinhToWord(String maHS) throws Exception {
        Map<String, Object> vars = new HashMap<>();
        HocSinhDTO dto = findHSByMaHS(maHS);
        var hs = dto.getThongTinHS();
        byte[] imageBytes = null;
        try {
            String folder = "uploads/hocsinh";
            String fileName = hs.getAnhDaiDien();
            if (fileName != null) {
                Path imagePath = Paths.get(System.getProperty("user.dir"), folder, fileName);
                if (Files.exists(imagePath)) {
                    imageBytes = Files.readAllBytes(imagePath);
                } else {
                    System.err.println("Không tìm thấy ảnh: " + imagePath.toAbsolutePath());
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi đọc ảnh: " + e.getMessage());
        }

        // --- nếu có ảnh, gắn vào placeholder ---
        if (imageBytes != null && imageBytes.length > 0) {
            vars.put("AnhDaiDien", Pictures.ofStream( new ByteArrayInputStream(imageBytes), PictureType.JPEG) .size(120, 160) .create());
        } else {
            vars.put("AnhDaiDien", null); // để trống nếu không có ảnh
        }
        vars.put("HoVaTen", hs.getHoVaChuLotHS() + " " + hs.getTenHS());
        vars.put("GioiTinh", hs.getGioiTinh() ? "Nữ" : "Nam");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        vars.put("NgaySinh", hs.getNgaySinh() != null ? hs.getNgaySinh().format(formatter) : "");
        vars.put("NoiSinh", hs.getNoiSinh() != null ? hs.getNoiSinh() : "");
        vars.put("DanToc", hs.getTenDT());
        vars.put("DiaChiThuongTru", hs.getDiaChiThuongTru() != null ? hs.getDiaChiThuongTru() : "");
        vars.put("ChoOHienNay", hs.getChoOHienNay() != null ? hs.getChoOHienNay() : "");
        vars.put("TruongTHCS", !Objects.equals(hs.getMaTHCS(), "0") ? hs.getTenTHCS() : hs.getTenTHCSNgoaiTPCT());
        vars.put("PhuongXa", !Objects.equals(hs.getMaTHCS(), "0") ? lowerFirst(hs.getTenPhuongXaTHCS()) : lowerFirst(hs.getTenXaNgoaiTPCT()));
        vars.put("TinhThanh", !Objects.equals(hs.getMaTHCS(), "0") ? "thành phố Cần Thơ" : lowerFirst(hs.getTenTinhNgoaiTPCT()));
        vars.put("NamTotNghiepTHCS", (hs.getNamTotNghiepTHCS()-1) + "-" + hs.getNamTotNghiepTHCS());
        vars.put("DTKKChuyen", hs.getTenDTKKChuyen());
        vars.put("DTUT", hs.getTenDTUT());
        vars.put("DTKK", hs.getTenDTKK());
        vars.put("SoDienThoai", hs.getSoDienThoai());
        vars.put("NNA", Objects.equals(hs.getNgoaiNguDangHoc(), "Tiếng Anh") ? "☑" : "☐");
        vars.put("NNP", Objects.equals(hs.getNgoaiNguDangHoc(), "Tiếng Pháp") ? "☑" : "☐");
        vars.put("NTA", Objects.equals(hs.getNgoaiNguDuThi(), "Tiếng Anh") ? "☑" : "☐");
        vars.put("NTP", Objects.equals(hs.getNgoaiNguDuThi(), "Tiếng Pháp") ? "☑" : "☐");

        for (KQHocTap kq : dto.getKqHocTap()) {
            vars.put("HT" + kq.getId().getLop(), kq.getHocTap());
            vars.put("RL" + kq.getId().getLop(), kq.getRenLuyen());
        }

        if (!dto.getNguyenVong().isEmpty()) {
            vars.put("TruongTHPTThi", dto.getNguyenVong().get(0).getTenTHPT());
        }

        for (int i = 1; i <= 5; i++) {
            vars.put("NV" + i, "(không đăng ký)");
        }
        vars.put("NV2b", "(không đăng ký)"); // nếu có NV2B
        vars.put("DTBChuyen", "(không đăng ký)");
        vars.put("MonChuyen", "(không đăng ký)");
        vars.put("LTP", "☐");

        for (NguyenVongFullData nv : dto.getNguyenVong()) {
            if (nv.getThuTu() == 1) {
                vars.put("NV1", "Lớp " + lowerFirst(nv.getTenLopChuyen()) + ", Trường " + nv.getTenTHPT());
                vars.put("MonChuyen", nv.getMonChuyen());
                vars.put("DTBChuyen", String.valueOf(hs.getDiemTBMonChuyen()));
            } else if (nv.getThuTu() == 2) {
                if (Boolean.TRUE.equals(nv.getNv2B())) {
                    vars.put("NV2b", nv.getTenTHPT());
                } else {
                    vars.put("NV2", nv.getTenTHPT());
                    vars.put("LTP", nv.getLopTiengPhap() ? "☑" : "☐");
                }
            } else {
                vars.put("NV" + nv.getThuTu(), nv.getTenTHPT());
            }
        }

        ClassPathResource resource = new ClassPathResource("static/templates/PhieuDangKy.docx");
        Configure config = Configure.builder()
                .bind("AnhDaiDien", new PictureRenderPolicy())
                .build();
        XWPFTemplate template = XWPFTemplate.compile(resource.getInputStream(), config).render(vars);
        // Xuất file ra mảng byte
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        template.write(out);
        template.close();
        return out.toByteArray();
    }

    @Transactional(readOnly = true)
    public Resource loadAnhHocSinh(String maHS) throws IOException {
        HocSinh hs = hsRepository.findById(maHS)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy học sinh có mã: " + maHS));
        // Lấy tên file từ DB
        String fileName = hs.getAnhDaiDien();
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path filePath;
        // Nếu không có ảnh -> dùng ảnh mặc định
        if (fileName == null || fileName.isEmpty()) {
            filePath = uploadPath.resolve("default-avatar.jpg");
        } else {
            filePath = uploadPath.resolve(fileName);
            if (!Files.exists(filePath)) {
                // File bị mất -> fallback ảnh mặc định
                filePath = uploadPath.resolve("default-avatar.jpg");
            }
        }
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Không tìm thấy ảnh mặc định");
        }
        return new UrlResource(filePath.toUri());
    }

    /*
     * Các hàm thêm thông tin học sinh
     */

    @Transactional
    public HocSinhDTO initHocSinh(String maTHCS, InitHocSinhRequest thongTinHS) {
        String maTHCS_data = thongTinHS.getMaTHCS();
        if (maTHCS_data == null) throw new EntityNotFoundException("Không tìm thấy trường THCS của học sinh!");
        if (!Objects.equals(maTHCS, maTHCS_data))
            throw new AccessDeniedException("Bạn không được phép thêm học sinh không do trường của bạn quản lý!");
        if (hsRepository.existsById(thongTinHS.getMaHS())) {
            throw new AccessDeniedException("Mã học sinh đã tồn tại!");
        }
        HocSinh newHS = new HocSinh();
        newHS.setMaHS(thongTinHS.getMaHS());
        newHS.setHoVaChuLotHS(thongTinHS.getHoVaChuLotHS());
        newHS.setTenHS(thongTinHS.getTenHS());
        newHS.setGioiTinh(thongTinHS.getGioiTinh());
        newHS.setNgaySinh(thongTinHS.getNgaySinh());
        newHS.setNoiSinh(thongTinHS.getNoiSinh());
        newHS.setMaTHCS(thongTinHS.getMaTHCS());
        HocSinh hs = hsRepository.save(newHS);
        if (!tkRepository.existsById(hs.getMaHS())) {
            String newPass = commonUtil.generateRandomPassword(8);
            String hashedPass = passwordEncoder.encode(newPass);

            TaiKhoan tk = new TaiKhoan(hs.getMaHS(), newPass, 4, hs.getMaHS(), hashedPass, false);
            tkRepository.save(tk);
        }
        HocSinhFullData hsFull = hsRepository.findHocSinhByMaHS(hs.getMaHS())
                .orElse(null);
        return new HocSinhDTO(hsFull, Collections.emptyList(), Collections.emptyList());
    }

    @Transactional
    public HocSinhDTO addHocSinh(HocSinhRequest hsRequest, MultipartFile file) throws IOException {
        // Kiểm tra tồn tại học sinh
        boolean found = hsRepository.existsById(hsRequest.getThongTinHS().getMaHS());
        if (found) {
            throw new AccessDeniedException("Học sinh đã tồn tại trong danh sách!");
        }
        // Xử lý file upload
        String fileName = saveAnhDaiDien(hsRequest.getThongTinHS().getMaHS(), file);
        if (fileName != null) {
            hsRequest.getThongTinHS().setAnhDaiDien(fileName);
        }
        // Xử lý dữ liệu JSON
        // Thêm vào bảng HocSinh
        hsRepository.save(hsRequest.getThongTinHS());
        String maHS = hsRequest.getThongTinHS().getMaHS();
        HocSinhFullData hsf = hsRepository.findHocSinhByMaHS(maHS).orElse(null);
        // Thêm vào bảng KQHocTap
        List<KQHocTap> kqht = kqhtRepository.saveAll(hsRequest.getKqHocTap());
        // Thêm vào bảng NguyenVong
        nvRepository.saveAll(hsRequest.getNguyenVong());
        List<NguyenVongFullData> nvf = nvRepository.findNguyenVongByMaHS(maHS);
        // Thêm tài khoản mới (nếu chưa tạo)
        if (!tkRepository.existsById(maHS)) {
            String newPass = commonUtil.generateRandomPassword(8);
            String hashedPass = passwordEncoder.encode(newPass);
            TaiKhoan tk = new TaiKhoan(maHS, newPass, 4, maHS, hashedPass, false);
            tkRepository.save(tk);
        }
        // Thêm vào bảng ThiSinh để truy xuất dữ liệu thí sinh
        if (hsRequest.getNguyenVong().get(0).getMaTHPT() != null) {
            NguyenVong data = hsRequest.getNguyenVong().get(0);
            ThiSinh ts = new ThiSinh(maHS, data.getMaTHPT(), data.getMaLopChuyen(), null, null, null);
            tsRepository.save(ts);
        }
        return new HocSinhDTO(hsf, kqht, nvf);
    }

    @Transactional
    public HocSinhDTO addHocSinh_THCS(String maTHCS, HocSinhRequest hsRequest, MultipartFile file) throws IOException {
        String maTHCS_data = hsRequest.getThongTinHS().getMaTHCS();
        if (maTHCS_data == null) throw new EntityNotFoundException("Không tìm thấy trường THCS của học sinh!");
        if (!Objects.equals(maTHCS, maTHCS_data))
            throw new AccessDeniedException("Bạn không được phép thêm học sinh không do trường của bạn quản lý!");
        return addHocSinh(hsRequest, file);
    }

    @Transactional
    public HocSinhDTO addHocSinh_THPT(String maTHPT, HocSinhRequest hsRequest, MultipartFile file) throws IOException {
        List<NguyenVong> dsNV = hsRequest.getNguyenVong();
        if (dsNV == null || dsNV.isEmpty()) throw new EntityNotFoundException("Không tìm thấy nguyện vọng của học sinh!");
        NguyenVong nvCaoNhat = dsNV.stream()
                .min(Comparator.comparingInt(nv -> nv.getId().getThuTu()))
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nguyện vọng cao nhất!"));
        if (!Objects.equals(nvCaoNhat.getMaTHPT(), maTHPT)) {
            throw new AccessDeniedException("Bạn không được phép thêm học sinh không đặt NV cao nhất vào trường của bạn quản lý!");
        }
        return addHocSinh(hsRequest, file);
    }

    @Transactional
    public ImportHocSinhDTO importInitHocSinh(String maTHCS, MultipartFile excelFile) {
        var thcs = thcsRepository.findById(maTHCS).orElseThrow(() -> new IllegalArgumentException("Mã THCS không hợp lệ!"));
        if (Objects.equals(thcs.getMaTHCS(), "0")) throw new AccessDeniedException("Trường này không được phép import học sinh!");
        ImportHocSinhDTO result = new ImportHocSinhDTO();
        try (InputStream is = excelFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) throw new IllegalArgumentException("Không tìm thấy sheet đầu tiên trong file Excel!");
            int rowNum = 0;
            for (Row row : sheet) {
                rowNum++;
                if (rowNum == 1) continue;
                String maHS = null;
                try {
                    maHS = commonUtil.getCellValueAsString(row.getCell(0));
                    if (maHS == null || maHS.trim().isEmpty()) {
                        result.getErrorList().add("Sheet ImportHocSinh - Dòng " + rowNum + ": Thiếu mã HS!");
                        continue;
                    }
                    String hoVaChuLotHS = commonUtil.getCellValueAsString(row.getCell(1));
                    if (hoVaChuLotHS == null || hoVaChuLotHS.trim().isEmpty()) {
                        throw new IllegalArgumentException("Họ và chữ lót không được để trống tại dòng " + (row.getRowNum() + 1));
                    }
                    String tenHS = commonUtil.getCellValueAsString(row.getCell(2));
                    if (tenHS == null || tenHS.trim().isEmpty()) {
                        throw new IllegalArgumentException("Tên học sinh không được để trống tại dòng " + (row.getRowNum() + 1));
                    }
                    Boolean gioiTinh = commonUtil.convertStringToBoolean(commonUtil.getCellValueAsString(row.getCell(3)));
                    LocalDate ngaySinh = commonUtil.getCellValueAsDate(row.getCell(4));
                    String noiSinh = commonUtil.getCellValueAsString(row.getCell(5));

                    HocSinh hs = new HocSinh();
                    hs.setMaHS(maHS);
                    hs.setHoVaChuLotHS(hoVaChuLotHS);
                    hs.setTenHS(tenHS);
                    hs.setGioiTinh(gioiTinh);
                    hs.setNgaySinh(ngaySinh);
                    hs.setNoiSinh(noiSinh);
                    hs.setMaTHCS(maTHCS);

                    hsRepository.save(hs);

                    if (!tkRepository.existsById(maHS)) {
                        String newPass = commonUtil.generateRandomPassword(8);
                        String hashedPass = passwordEncoder.encode(newPass);
                        TaiKhoan tk = new TaiKhoan(maHS, newPass, 4, maHS, hashedPass, false);
                        tkRepository.save(tk);
                    }

                    HocSinhFullData hsf = hsRepository.findHocSinhByMaHS(maHS).orElse(null);
                    HocSinhDTO dto = new HocSinhDTO(hsf, Collections.emptyList(), Collections.emptyList());
                    result.getSuccessList().add(dto);
                } catch (Exception ex) {
                    result.getErrorList().add("Lỗi khi xử lý học sinh: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File Excel không hợp lệ hoặc bị hỏng", e);
        }
        return result;
    }

    @Transactional
    public ImportHocSinhDTO importHocSinhTHCS(String maTHCS, MultipartFile excelFile, MultipartFile zipFile) throws IOException {
        var thcs = thcsRepository.findById(maTHCS).orElseThrow(() -> new IllegalArgumentException("Mã THCS không hợp lệ!"));
        if (Objects.equals(thcs.getMaTHCS(), "0")) throw new AccessDeniedException("Trường này không được phép import học sinh!");
        return importHocSinh(maTHCS, excelFile, zipFile);
    }

    @Transactional
    public ImportHocSinhDTO importHocSinhTHPT(String maTHPT, MultipartFile excelFile, MultipartFile zipFile) throws IOException {
        var thpt = thptRepository.findById(maTHPT)
                .orElseThrow(() -> new IllegalArgumentException("Mã THPT không hợp lệ!"));
        if (!Boolean.TRUE.equals(thpt.getTsNgoaiTPCT()))
            throw new AccessDeniedException("Trường này không được phép import học sinh!");
        String maTHCSDinhDanh = "0";
        return importHocSinh(maTHCSDinhDanh, excelFile, zipFile);
    }

    @Transactional
    public ImportHocSinhDTO importHocSinh(String maTHCSDinhDanh, MultipartFile excelFile, MultipartFile zipFile) throws IOException {
        ImportHocSinhDTO result = new ImportHocSinhDTO();
        // Xử lý dữ liệu file ZIP (tạm thời, nếu không gửi file ZIP thì bỏ qua)
        Path tempDir = null;
        if (zipFile != null && !zipFile.isEmpty()) {
            tempDir = Files.createTempDirectory("upload_zip_");
            try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (!entry.isDirectory()) {
                        Path filePath = tempDir.resolve(entry.getName());
                        Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
        // 1. Đọc Excel (Apache POI/EasyExcel)
        try (InputStream is = excelFile.getInputStream();
            Workbook workbook = new XSSFWorkbook(is)) {
            Map<String, HocSinh> hocSinhMap = importHocSinhSheet(workbook.getSheet("ImportHocSinh"), maTHCSDinhDanh, result, tempDir);
            importKQHocTapSheet(workbook.getSheet("ImportKQHocTap"), hocSinhMap, result);
            importNguyenVongSheet(workbook.getSheet("ImportNguyenVong"), hocSinhMap, result);
            for (String maHS : hocSinhMap.keySet()) {
                if (result.getErrorByHS().containsKey(maHS)) {
                    continue;
                }
                // Lưu vào success list
                HocSinhFullData hsf = hsRepository.findHocSinhByMaHS(maHS).orElse(null);
                List<KQHocTap> kqht = kqhtRepository.findById_MaHS(maHS);
                List<NguyenVongFullData> nvf = nvRepository.findNguyenVongByMaHS(maHS);
                HocSinhDTO dto = new HocSinhDTO(hsf, kqht, nvf);
                result.getSuccessList().add(dto);
                // Lưu tài khoản (nếu chưa tạo)
                if (!tkRepository.existsById(maHS)) {
                    String newPass = commonUtil.generateRandomPassword(8);
                    String hashedPass = passwordEncoder.encode(newPass);
                    TaiKhoan tk = new TaiKhoan(maHS, newPass, 4, maHS, hashedPass, false);
                    tkRepository.save(tk);
                }
                // Lưu vào bảng ThiSinh với mã THPT tương ứng
                if (!nvf.isEmpty() && nvf.get(0).getMaTHPT() != null) {
                    NguyenVongFullData data = nvf.get(0);
                    ThiSinh ts = new ThiSinh(maHS, data.getMaTHPT(), data.getMaLopChuyen(), null, null, null);
                    tsRepository.save(ts);
                }
            }
            for (Map.Entry<String, List<String>> entry : result.getErrorByHS().entrySet()) {
                String maHS = entry.getKey();
                List<String> errs = entry.getValue();
                result.getErrorList().add("Mã HS " + maHS + " gặp lỗi:\n - " + String.join("\n - ", errs));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File Excel không hợp lệ hoặc bị hỏng", e);
        } finally {
            if (tempDir != null) FileSystemUtils.deleteRecursively(tempDir);
        }
        return result;
    }

    private Map<String, HocSinh> importHocSinhSheet(Sheet sheet, String maTHCSDinhDanh, ImportHocSinhDTO result, Path tempDir) {
        Map<String, HocSinh> hocSinhMap = new HashMap<>();
        if (sheet == null) return hocSinhMap;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            boolean isEmptyRow = true;
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    String cellVal = commonUtil.getCellValueAsString(cell);
                    if (cellVal != null && !cellVal.trim().isEmpty()) {
                        isEmptyRow = false;
                        break;
                    }
                }
            }
            if (isEmptyRow) continue; // bỏ qua hàng trống
            String maHS = null;
            try {
                maHS = commonUtil.getCellValueAsString(row.getCell(0));
                if (maHS == null || maHS.trim().isEmpty()) {
                    result.getErrorList().add("Sheet ImportHocSinh - Dòng " + (i + 1) + ": Thiếu mã HS!");
                    continue;
                }

                // Lấy dữ liệu
                String hoVaChuLotHS = commonUtil.getCellValueAsString(row.getCell(1));
                String tenHS = commonUtil.getCellValueAsString(row.getCell(2));
                if (hoVaChuLotHS == null || hoVaChuLotHS.trim().isEmpty()) {
                    throw new IllegalArgumentException("Họ và chữ lót không được để trống tại dòng " + (row.getRowNum() + 1));
                }
                if (tenHS == null || tenHS.trim().isEmpty()) {
                    throw new IllegalArgumentException("Tên học sinh không được để trống tại dòng " + (row.getRowNum() + 1));
                }
                Boolean gioiTinh = commonUtil.convertStringToBoolean(commonUtil.getCellValueAsString(row.getCell(3)));
                LocalDate ngaySinh = commonUtil.getCellValueAsDate(row.getCell(4));
                String noiSinh = commonUtil.getCellValueAsString(row.getCell(5));
                Integer maDT = commonUtil.getCellValueAsInt(row.getCell(6));
                String diaChiThuongTru = commonUtil.getCellValueAsString(row.getCell(7));
                String choOHienNay = commonUtil.getCellValueAsString(row.getCell(8));
                Integer maDTUT = commonUtil.getCellValueAsInt(row.getCell(9));
                Integer maDTKK = commonUtil.getCellValueAsInt(row.getCell(10));
                Integer maDTKKChuyen = commonUtil.getCellValueAsInt(row.getCell(11));
                String ngoaiNguDangHoc = commonUtil.getCellValueAsString(row.getCell(12));
                String ngoaiNguDuThi = commonUtil.getCellValueAsString(row.getCell(13));
                String soDienThoai = commonUtil.getCellValueAsString(row.getCell(14));
                BigDecimal diemTBMonChuyen = commonUtil.getCellValueAsBigDecimal(row.getCell(15));
                BigDecimal tongDTBLop9 = commonUtil.getCellValueAsBigDecimal(row.getCell(16));
                String tenTHCSNgoaiTPCT = commonUtil.getCellValueAsString(row.getCell(17));
                String tenXaNgoaiTPCT = commonUtil.getCellValueAsString(row.getCell(18));
                String tenTinhNgoaiTPCT = commonUtil.getCellValueAsString(row.getCell(19));

                HocSinh hs = new HocSinh();
                hs.setMaHS(maHS);
                hs.setHoVaChuLotHS(hoVaChuLotHS);
                hs.setTenHS(tenHS);
                hs.setGioiTinh(gioiTinh);
                hs.setNgaySinh(ngaySinh);
                hs.setNoiSinh(noiSinh);
                hs.setMaTHCS(maTHCSDinhDanh);
                hs.setNamTotNghiepTHCS(2026);
                hs.setDiaChiThuongTru(diaChiThuongTru);
                hs.setChoOHienNay(choOHienNay);
                hs.setMaDT(maDT);
                hs.setMaDTUT(maDTUT);
                hs.setMaDTKK(maDTKK);
                hs.setMaDTKKChuyen(maDTKKChuyen);
                hs.setNgoaiNguDangHoc(ngoaiNguDangHoc);
                hs.setNgoaiNguDuThi(ngoaiNguDuThi);
                hs.setSoDienThoai(soDienThoai);
                hs.setDiemTBMonChuyen(diemTBMonChuyen);
                hs.setTongDiemTBLop9(tongDTBLop9);
                hs.setTenTHCSNgoaiTPCT(tenTHCSNgoaiTPCT);
                hs.setTenXaNgoaiTPCT(tenXaNgoaiTPCT);
                hs.setTenTinhNgoaiTPCT(tenTinhNgoaiTPCT);
                String fileName = null;
                // xử lý ảnh
                if (tempDir != null) {
                    try (Stream<Path> files = Files.list(tempDir)) {
                        String finalMaHS = maHS;
                        Optional<Path> matchedFile = files
                                .filter(p -> {
                                    String n = p.getFileName().toString().toLowerCase();
                                    return n.startsWith(finalMaHS.toLowerCase() + ".");
                                })
                                .findFirst();

                        if (matchedFile.isPresent()) {
                            Path filePath = matchedFile.get();
                            MultipartFile multipartFile = new MockMultipartFile(
                                    "file",
                                    filePath.getFileName().toString(),
                                    Files.probeContentType(filePath),
                                    Files.readAllBytes(filePath)
                            );
                            fileName = saveAnhDaiDien(maHS, multipartFile);
                        } else {
                            System.out.println("Không tìm thấy ảnh cho MSHS: " + maHS);
                        }
                    }
                }
                hs.setAnhDaiDien(fileName);
                hsRepository.save(hs);
                hocSinhMap.put(maHS, hs);
            } catch (Exception ex) {
                result.getErrorList().add("Sheet ImportHocSinh - Dòng " + (i+1) + ": " + ex.getMessage());
                result.getErrorByHS().computeIfAbsent(maHS, k -> new ArrayList<>())
                        .add("ImportHocSinh: " + ex.getMessage());
            }
        }
        return hocSinhMap;
    }

    private void importKQHocTapSheet(Sheet sheet, Map<String, HocSinh> hocSinhMap, ImportHocSinhDTO result) {
        if (sheet == null) return;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            boolean isEmptyRow = true;
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    String cellVal = commonUtil.getCellValueAsString(cell);
                    if (cellVal != null && !cellVal.trim().isEmpty()) {
                        isEmptyRow = false;
                        break;
                    }
                }
            }
            if (isEmptyRow) continue; // bỏ qua hàng trống
            String maHS = null;
            try {
                maHS = commonUtil.getCellValueAsString(row.getCell(0));
                if (!hocSinhMap.containsKey(maHS)) {
                    result.getErrorList().add("Sheet ImportKQHocTap - Dòng " + (i + 1) + ": Mã HS không tồn tại trong sheet ImportHocSinh!");
                    continue;
                }
                Integer lop = commonUtil.getCellValueAsInt(row.getCell(1));
                if (lop == null) {
                    result.getErrorList().add("Sheet ImportKQHocTap - Dòng " + (i + 1) + ": Chưa nhập giá trị của lớp!");
                    result.getErrorByHS().computeIfAbsent(maHS, k -> new ArrayList<>())
                            .add("ImportKQHocTap: Chưa nhập giá trị của lớp!");
                    continue;
                }
                String hocTap = commonUtil.getCellValueAsString(row.getCell(2));
                String renLuyen = commonUtil.getCellValueAsString(row.getCell(3));
                KQHocTapID id = new KQHocTapID(maHS, lop);
                kqhtRepository.save(new KQHocTap(id, hocTap, renLuyen));
            } catch (Exception ex) {
                result.getErrorList().add("Sheet ImportKQHocTap - Dòng " + (i + 1) + ": " + ex.getMessage());
                result.getErrorByHS().computeIfAbsent(maHS, k -> new ArrayList<>())
                        .add("ImportKQHocTap: " + ex.getMessage());
            }
        }
    }

    private void importNguyenVongSheet(Sheet sheet, Map<String, HocSinh> hocSinhMap, ImportHocSinhDTO result) {
        if (sheet == null) return;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            boolean isEmptyRow = true;
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    String cellVal = commonUtil.getCellValueAsString(cell);
                    if (cellVal != null && !cellVal.trim().isEmpty()) {
                        isEmptyRow = false;
                        break;
                    }
                }
            }
            if (isEmptyRow) continue; // bỏ qua hàng trống
            String maHS = null;
            try {
                maHS = commonUtil.getCellValueAsString(row.getCell(0));
                if (!hocSinhMap.containsKey(maHS)) {
                    result.getErrorList().add("Sheet ImportNguyenVong - Dòng " + (i + 1) + ": Mã HS không tồn tại trong sheet ImportHocSinh!");
                    continue;
                }
                Integer thuTu = commonUtil.getCellValueAsInt(row.getCell(1));
                if (thuTu == null) {
                    result.getErrorList().add("Sheet ImportNguyenVong - Dòng " + (i + 1) + ": Chưa nhập thứ tự nguyện vọng!");
                    result.getErrorByHS().computeIfAbsent(maHS, k -> new ArrayList<>())
                            .add("ImportNguyenVong: Chưa nhập thứ tự nguyện vọng!");
                    continue;
                }
                String maTHPT = commonUtil.getCellValueAsString(row.getCell(2));
                Boolean nv2B = commonUtil.convertStringToBoolean(commonUtil.getCellValueAsString(row.getCell(3)));
                Boolean lopTiengPhap = commonUtil.convertStringToBoolean(commonUtil.getCellValueAsString(row.getCell(4)));
                String maLopChuyen = commonUtil.getCellValueAsString(row.getCell(5));

                NguyenVongID id = new NguyenVongID(maHS, thuTu);
                NguyenVong nv = new NguyenVong(id, maTHPT, nv2B, lopTiengPhap, maLopChuyen, null);
                nvRepository.save(nv);

            } catch (Exception ex) {
                result.getErrorList().add("Sheet ImportNguyenVong - Dòng " + (i + 1) + ": " + ex.getMessage());
                result.getErrorByHS().computeIfAbsent(maHS, k -> new ArrayList<>())
                        .add("ImportNguyenVong: " + ex.getMessage());
            }
        }
    }


    /*
     * Các hàm cập nhật thông tin học sinh
     */

    @Transactional
    public HocSinhDTO updateHocSinh(String maHS, HocSinhRequest hsRequest, MultipartFile file) throws IOException {
        // Kiểm tra sự tồn tại của thông tin học sinh
        HocSinh existingHS = hsRepository.findById(maHS)
                .orElseThrow(() -> new EntityNotFoundException("Học sinh có mã số " + maHS + " không tồn tại!"));
        HocSinh hs = hsRequest.getThongTinHS();
        if (!Objects.equals(hs.getMaHS(), maHS)) throw new IllegalArgumentException("Bạn không được phép sửa mã học sinh!");
        // Cập nhật ảnh đại diện
        // Nếu không đổi ảnh đại diện, mặc định giữ nguyên - Nếu có tick vào xóa, sẽ xóa hình cũ
        boolean removeImage = Boolean.TRUE.equals(hsRequest.getRemoveImage());
        Path path = Paths.get(uploadDir);
        if (removeImage) { // Xóa file vật lý nếu có
            if (existingHS.getAnhDaiDien() != null && !existingHS.getAnhDaiDien().isEmpty()) {
                Path oldFilePath = path.resolve(existingHS.getAnhDaiDien());
                Files.deleteIfExists(oldFilePath);
            }
            hs.setAnhDaiDien(null); // đặt về null trong DB
        } else if (file != null && !file.isEmpty()) { // Xóa ảnh cũ (nếu có)
            if (existingHS.getAnhDaiDien() != null && !existingHS.getAnhDaiDien().isEmpty()) {
                Path oldFilePath = path.resolve(existingHS.getAnhDaiDien());
                Files.deleteIfExists(oldFilePath);
            }
            // Lấy ảnh mới
            String newFileName = saveAnhDaiDien(maHS, file);
            hs.setAnhDaiDien(newFileName);
        } else {
            hs.setAnhDaiDien(existingHS.getAnhDaiDien()); // Giữ ảnh cũ
        }
        // Xóa dữ liệu cũ của học sinh
        kqhtRepository.deleteById_MaHS(maHS);
        nvRepository.deleteById_MaHS(maHS);
        // Thêm, cập nhật dữ liệu mới vào CSDL
        hsRepository.save(hs);
        kqhtRepository.saveAll(hsRequest.getKqHocTap());
        nvRepository.saveAll(hsRequest.getNguyenVong());
        // Lấy và cập nhật các dữ liệu cần thiết (nếu để trống, mặc định là null)
        HocSinhFullData hsf = hsRepository.findHocSinhByMaHS(maHS).orElse(null);
        List<KQHocTap> kqht = kqhtRepository.findById_MaHS(maHS);
        List<NguyenVongFullData> nvf = nvRepository.findNguyenVongByMaHS(maHS);

        // Cập nhật vào bảng ThiSinh để truy xuất dữ liệu thí sinh
        if (nvf.get(0).getMaTHPT() != null) {
            NguyenVongFullData data = nvf.get(0);
            ThiSinh ts = new ThiSinh(maHS, data.getMaTHPT(), data.getMaLopChuyen(), null, null, null);
            tsRepository.save(ts);
        }
        return new HocSinhDTO(hsf, kqht, nvf);
    }

    @Transactional
    public HocSinhDTO updateHocSinh_RoleTHCS(String maTHCS, String maHS, HocSinhRequest hsRequest, MultipartFile file) throws IOException {
        if (hsRepository.existsByMaHSAndMaTHCS(maHS, maTHCS)) return updateHocSinh(maHS, hsRequest, file);
        else throw new EntityNotFoundException("Không tìm thấy học sinh có mã số " + maHS);
    }

    @Transactional
    public HocSinhDTO updateHocSinh_RoleTHPT(String maTHPT, String maHS, HocSinhRequest hsRequest, MultipartFile file) throws IOException {
        if (hsRepository.existsHocSinhNgoaiTinhByMaHSAndMaTHPT(maHS, maTHPT)) return updateHocSinh(maHS, hsRequest, file);
        else throw new EntityNotFoundException("Không tìm thấy học sinh có mã số " + maHS);
    }

    /*
    * Các hàm xóa thông tin học sinh
    */

    @Transactional
    public void deleteHocSinh(String maHS) {
        // Kiếm tra tồn tại học sinh
        HocSinh hs = hsRepository.findById(maHS)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy học sinh có mã số " + maHS));
        // Xóa ảnh đại diện của học sinh
        String fileName = hs.getAnhDaiDien();
        if (fileName != null && !fileName.isEmpty()) {
            try {
                Path uploadPath = Paths.get(uploadDir);
                Path filePath = uploadPath.resolve(fileName);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Không thể xóa ảnh đại diện của học sinh " + maHS + ": " + e.getMessage());
            }
        }
        // Xóa thông tin của học sinh (kéo theo xóa các bảng có liên quan)
        hsRepository.delete(hs);
        // Xóa tài khoản của học sinh
        tkRepository.deleteById(maHS);
    }

    @Transactional
    public void deleteHocSinh_RoleTHCS(String maTHCS, String maHS) {
        if (hsRepository.existsByMaHSAndMaTHCS(maHS, maTHCS)) deleteHocSinh(maHS);
        else throw new EntityNotFoundException("Không tìm thấy học sinh có mã số " + maHS);
    }

    @Transactional
    public void deleteHocSinh_RoleTHPT(String maTHPT, String maHS) {
        if (hsRepository.existsHocSinhNgoaiTinhByMaHSAndMaTHPT(maHS, maTHPT)) deleteHocSinh(maHS);
        else throw new EntityNotFoundException("Không tìm thấy học sinh có mã số " + maHS);
    }

    @Transactional
    public void deleteHocSinhBymaTHCS(String maTHCS) {
        List<String> lhs = hsRepository.findMaHSByMaTHCS(maTHCS);
        for (String hs : lhs) deleteHocSinh(hs);
    }

    @Transactional
    public void deleteHocSinhNgoaiTinhBymaTHPT(String maTHPT) {
        List<String> lhs = hsRepository.findMaHSNgoaiTinhByMaTHPT(maTHPT);
        for (String hs : lhs) deleteHocSinh(hs);
    }
}
