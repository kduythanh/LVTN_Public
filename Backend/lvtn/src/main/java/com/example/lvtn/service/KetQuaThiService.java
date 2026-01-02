package com.example.lvtn.service;

import com.example.lvtn.common.CommonUtil;
import com.example.lvtn.dto.*;
import com.example.lvtn.model.KetQuaThi;
import com.example.lvtn.model.NguyenVong;
import com.example.lvtn.model.NguyenVongID;
import com.example.lvtn.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class KetQuaThiService {
    private final KetQuaThiRepository kqtRepository;
    private final NguyenVongRepository nvRepository;
    private final THPTRepository thptRepository;
    private final LopChuyenTHPTRepository lcthptRepository;
    private final ThiSinhRepository tsRepository;
    private final CommonUtil commonUtil;

    public KetQuaThiService(KetQuaThiRepository kqtRepository, NguyenVongRepository nvRepository, THPTRepository thptRepository,
                             LopChuyenTHPTRepository lcthptRepository, ThiSinhRepository tsRepository, CommonUtil commonUtil) {
        this.kqtRepository = kqtRepository;
        this.nvRepository = nvRepository;
        this.thptRepository = thptRepository;
        this.lcthptRepository = lcthptRepository;
        this.tsRepository = tsRepository;
        this.commonUtil = commonUtil;
    }

    @Transactional
    public KetQuaThi updateKQThi(String soBaoDanh, KetQuaThi kqThi) {
        KetQuaThiDTO existTS = tsRepository.findTSBySoBaoDanh(soBaoDanh);
        if (existTS == null)
            throw new EntityNotFoundException("Không tìm thấy thí sinh với số báo danh " + soBaoDanh);
        if (!Objects.equals(existTS.getMaHS(), kqThi.getMaHS()))
            throw new IllegalArgumentException("Mã học sinh không trùng khớp, không thể cập nhật!");
        KetQuaThi existKQT = kqtRepository.findById(kqThi.getMaHS())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kết quả thi cho học sinh: " + kqThi.getMaHS()));
        existKQT.setDiemToan(kqThi.getDiemToan());
        existKQT.setDiemVan(kqThi.getDiemVan());
        existKQT.setDiemMonThu3(kqThi.getDiemMonThu3());
        existKQT.setDiemMonChuyen(kqThi.getDiemMonChuyen());
        return kqtRepository.save(existKQT);
    }

    @Transactional
    public ImportKetQuaThiDTO importKQThiFromExcel(MultipartFile file) {
        ImportKetQuaThiDTO result = new ImportKetQuaThiDTO();
        Map<String, ThiSinhDTO> tsMap = tsRepository.findAllTS()
                .stream()
                .collect(Collectors.toMap(ThiSinhDTO::getSoBaoDanh, Function.identity()));
        // 1. Đọc Excel (Apache POI/EasyExcel)
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            // 2. Map mỗi row
            // Bỏ qua dòng đầu tiên (tiêu đề)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                try {
                    String SBD = row.getCell(0).getStringCellValue().trim();
                    if (SBD.isEmpty()) {
                        throw new IllegalArgumentException("Dòng " + (i+1) + " thiếu số báo danh");
                    }
                    ThiSinhDTO ts = tsMap.get(SBD);
                    if (ts == null)
                        throw new EntityNotFoundException("Không tìm thấy thí sinh có SBD " + SBD);

                    KetQuaThi kqThi = new KetQuaThi();
                    kqThi.setMaHS(ts.getMaHS());

                    BigDecimal diemToan = commonUtil.getCellValueAsBigDecimal(row.getCell(1));
                    commonUtil.validateDiem(diemToan, "môn Toán");
                    kqThi.setDiemToan(diemToan);

                    BigDecimal diemVan = commonUtil.getCellValueAsBigDecimal(row.getCell(2));
                    commonUtil.validateDiem(diemVan, "môn Ngữ văn");
                    kqThi.setDiemVan(diemVan);

                    BigDecimal diemMonThu3 = commonUtil.getCellValueAsBigDecimal(row.getCell(3));
                    commonUtil.validateDiem(diemMonThu3, "môn thứ 3");
                    kqThi.setDiemMonThu3(diemMonThu3);

                    if (Boolean.TRUE.equals(ts.getThptChuyen())) {
                        BigDecimal diemMonChuyen = commonUtil.getCellValueAsBigDecimal(row.getCell(4));
                        commonUtil.validateDiem(diemMonChuyen, "môn chuyên");
                        kqThi.setDiemMonChuyen(diemMonChuyen);
                    } else kqThi.setDiemMonChuyen(null);
                    // 3. Lưu vào DB
                    kqtRepository.save(kqThi);
                    result.getSuccessList().add(kqThi);
                } catch (Exception ex) {
                    // Ghi nhận lỗi cho từng dòng
                    result.getErrorList().add("Dòng " + (i+1) + ": " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File Excel không hợp lệ hoặc bị hỏng", e);
        }
        // 4. Trả về kết quả điểm thi đã cập nhật
        return result;
    }

    @Transactional
    public byte[] exportKQThiToExcel() throws IOException {
        List<ThiSinhDTO> kqt = tsRepository.findAllTS();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("KetQuaThi");

            // Định dạng ô header (in đậm + nền + border)
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);

            // Định dạng ô chứa dữ liệu text (có viền + canh trái)
            CellStyle textStyle = workbook.createCellStyle();
            textStyle.setAlignment(HorizontalAlignment.LEFT);
            textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Định dạng ô chứa điểm (số thập phân)
            CellStyle decimalStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            decimalStyle.setDataFormat(format.getFormat("0.00"));
            decimalStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Định dạng ô bị khoá hoặc không hợp lệ (nền xám)
            CellStyle disabledStyle = workbook.createCellStyle();
            disabledStyle.cloneStyleFrom(decimalStyle);
            disabledStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            disabledStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            disabledStyle.setAlignment(HorizontalAlignment.CENTER);
            disabledStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Số báo danh", "Điểm môn Toán", "Điểm môn Văn", "Điểm môn thứ 3", "Điểm môn chuyên" };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (ThiSinhDTO kq : kqt) {
                Row row = sheet.createRow(rowIdx++);
                Cell soBaoDanhCell = row.createCell(0);
                soBaoDanhCell.setCellValue(kq.getSoBaoDanh());
                soBaoDanhCell.setCellStyle(textStyle);
                commonUtil.setCellValueAsDecimal(row, 1, kq.getDiemToan(), workbook);
                commonUtil.setCellValueAsDecimal(row, 2, kq.getDiemVan(), workbook);
                commonUtil.setCellValueAsDecimal(row, 3, kq.getDiemMonThu3(), workbook);
                commonUtil.setCellValueAsDecimal(row, 4, kq.getDiemMonChuyen(), workbook);

                Cell diemMonChuyenCell = row.createCell(4);
                if (Boolean.TRUE.equals(kq.getThptChuyen())) {
                    diemMonChuyenCell.setCellStyle(decimalStyle);
                    if (kq.getDiemMonChuyen() != null) commonUtil.setCellValueAsDecimal(row, 4, kq.getDiemToan(), workbook);
                } else {
                    diemMonChuyenCell.setCellValue("-");
                    diemMonChuyenCell.setCellStyle(disabledStyle);
                }
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
    public byte[] exportKQTS() throws IOException {
        List<KetQuaTuyenSinhDTO> kqt = tsRepository.findKQTS();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("KetQuaTuyenSinh");

            // Định dạng ô header (in đậm + nền + border)
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);

            // Định dạng ô chứa dữ liệu text (có viền + canh trái)
            CellStyle textStyle = workbook.createCellStyle();
            textStyle.setAlignment(HorizontalAlignment.LEFT);
            textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Định dạng ô chứa điểm (số thập phân)
            CellStyle decimalStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            decimalStyle.setDataFormat(format.getFormat("0.00"));
            decimalStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Định dạng ô bị khoá hoặc không hợp lệ (nền xám)
            CellStyle disabledStyle = workbook.createCellStyle();
            disabledStyle.cloneStyleFrom(decimalStyle);
            disabledStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            disabledStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            disabledStyle.setAlignment(HorizontalAlignment.CENTER);
            disabledStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Số báo danh", "Họ và chữ lót", "Tên", "Trường THCS", "Hội đồng thi",
                    "Điểm cộng ưu tiên", "Điểm cộng khuyến khích", "Điểm cộng khuyến khich chuyên",
                    "Điểm môn Toán", "Điểm môn Văn", "Điểm môn thứ 3", "Điểm môn chuyên", "Tổng điểm", "Tổng điểm chuyên", "Nguyện vọng đậu", "Trường THPT đậu" };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (KetQuaTuyenSinhDTO kq : kqt) {
                Row row = sheet.createRow(rowIdx++);
                Cell soBaoDanhCell = row.createCell(0);
                soBaoDanhCell.setCellValue(kq.getSoBaoDanh());
                soBaoDanhCell.setCellStyle(textStyle);
                Cell hoVaChuLotCell = row.createCell(1);
                hoVaChuLotCell.setCellValue(kq.getHoVaChuLotHS());
                hoVaChuLotCell.setCellStyle(textStyle);
                Cell tenCell = row.createCell(2);
                tenCell.setCellValue(kq.getTenHS());
                tenCell.setCellStyle(textStyle);
                Cell truongTHCSCell = row.createCell(3);
                truongTHCSCell.setCellValue(kq.getTenTHCS());
                truongTHCSCell.setCellStyle(textStyle);
                Cell truongTHPTCell = row.createCell(4);
                truongTHPTCell.setCellValue(kq.getTenTHPT());
                truongTHPTCell.setCellStyle(textStyle);
                commonUtil.setCellValueAsDecimal(row, 5, kq.getDiemCongUuTien(), workbook);
                commonUtil.setCellValueAsDecimal(row, 6, kq.getDiemCongKhuyenKhich(), workbook);
                commonUtil.setCellValueAsDecimal(row, 7, kq.getDiemCongKhuyenKhichChuyen(), workbook);
                commonUtil.setCellValueAsDecimal(row, 8, kq.getDiemToan(), workbook);
                commonUtil.setCellValueAsDecimal(row, 9, kq.getDiemVan(), workbook);
                commonUtil.setCellValueAsDecimal(row, 10, kq.getDiemMonThu3(), workbook);
                commonUtil.setCellValueAsDecimal(row, 11, kq.getDiemMonChuyen(), workbook);
                commonUtil.setCellValueAsDecimal(row, 12, kq.getTongDiemChung(), workbook);
                commonUtil.setCellValueAsDecimal(row, 13, kq.getTongDiemChuyen(), workbook);
                Cell nvDauCell = row.createCell(14);
                if (kq.getNguyenVongDau() != null) {
                    nvDauCell.setCellValue(kq.getNguyenVongDau());
                }
                nvDauCell.setCellStyle(textStyle);
                Cell truongTHPTDauCell = row.createCell(15);
                if (kq.getTruongTHPTDau() != null) {
                    truongTHPTDauCell.setCellValue(kq.getTruongTHPTDau());
                } else {
                    truongTHPTDauCell.setCellValue("Không trúng tuyển");
                }
                truongTHPTDauCell.setCellStyle(textStyle);
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
    public byte[] exportKQTSByTHPT(String maTHPT) throws IOException {
        List<KetQuaTuyenSinhDTO> kqt = tsRepository.findKQTSByMaTHPT(maTHPT);

        try (Workbook workbook = new XSSFWorkbook()) {
            String s = "KetQuaTuyenSinh_" + maTHPT;
            Sheet sheet = workbook.createSheet(s);

            // Định dạng ô header (in đậm + nền + border)
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);

            // Định dạng ô chứa dữ liệu text (có viền + canh trái)
            CellStyle textStyle = workbook.createCellStyle();
            textStyle.setAlignment(HorizontalAlignment.LEFT);
            textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Định dạng ô chứa điểm (số thập phân)
            CellStyle decimalStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            decimalStyle.setDataFormat(format.getFormat("0.00"));
            decimalStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Định dạng ô bị khoá hoặc không hợp lệ (nền xám)
            CellStyle disabledStyle = workbook.createCellStyle();
            disabledStyle.cloneStyleFrom(decimalStyle);
            disabledStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            disabledStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            disabledStyle.setAlignment(HorizontalAlignment.CENTER);
            disabledStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Số báo danh", "Họ và chữ lót", "Tên", "Trường THCS", "Hội đồng thi",
                    "Điểm cộng ưu tiên", "Điểm cộng khuyến khích", "Điểm cộng khuyến khich chuyên",
                    "Điểm môn Toán", "Điểm môn Văn", "Điểm môn thứ 3", "Điểm môn chuyên", "Tổng điểm", "Tổng điểm chuyên", "Nguyện vọng đậu", "Trường THPT đậu" };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (KetQuaTuyenSinhDTO kq : kqt) {
                Row row = sheet.createRow(rowIdx++);
                Cell soBaoDanhCell = row.createCell(0);
                soBaoDanhCell.setCellValue(kq.getSoBaoDanh());
                soBaoDanhCell.setCellStyle(textStyle);
                Cell hoVaChuLotCell = row.createCell(1);
                hoVaChuLotCell.setCellValue(kq.getHoVaChuLotHS());
                hoVaChuLotCell.setCellStyle(textStyle);
                Cell tenCell = row.createCell(2);
                tenCell.setCellValue(kq.getTenHS());
                tenCell.setCellStyle(textStyle);
                Cell truongTHCSCell = row.createCell(3);
                truongTHCSCell.setCellValue(kq.getTenTHCS());
                truongTHCSCell.setCellStyle(textStyle);
                Cell truongTHPTCell = row.createCell(4);
                truongTHPTCell.setCellValue(kq.getTenTHPT());
                truongTHPTCell.setCellStyle(textStyle);
                commonUtil.setCellValueAsDecimal(row, 5, kq.getDiemCongUuTien(), workbook);
                commonUtil.setCellValueAsDecimal(row, 6, kq.getDiemCongKhuyenKhich(), workbook);
                commonUtil.setCellValueAsDecimal(row, 7, kq.getDiemCongKhuyenKhichChuyen(), workbook);
                commonUtil.setCellValueAsDecimal(row, 8, kq.getDiemToan(), workbook);
                commonUtil.setCellValueAsDecimal(row, 9, kq.getDiemVan(), workbook);
                commonUtil.setCellValueAsDecimal(row, 10, kq.getDiemMonThu3(), workbook);
                commonUtil.setCellValueAsDecimal(row, 11, kq.getDiemMonChuyen(), workbook);
                commonUtil.setCellValueAsDecimal(row, 12, kq.getTongDiemChung(), workbook);
                commonUtil.setCellValueAsDecimal(row, 13, kq.getTongDiemChuyen(), workbook);
                Cell nvDauCell = row.createCell(14);
                if (kq.getNguyenVongDau() != null) {
                    nvDauCell.setCellValue(kq.getNguyenVongDau());
                }
                nvDauCell.setCellStyle(textStyle);
                Cell truongTHPTDauCell = row.createCell(15);
                if (kq.getTruongTHPTDau() != null) {
                    truongTHPTDauCell.setCellValue(kq.getTruongTHPTDau());
                } else {
                    truongTHPTDauCell.setCellValue("Không trúng tuyển");
                }
                truongTHPTDauCell.setCellStyle(textStyle);
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
    public void xetTuyen() {
        List<HocSinh_XT> lhs = kqtRepository.getBangDiemAllThiSinh();
        List<NguyenVong> lnv = nvRepository.findAll();
        for (NguyenVong nv : lnv) {
            nv.setKetQua(null);
        }
        List<THPT_XT> lthpt = thptRepository.getThongTinXT_THPT();
        List<LopChuyenTHPT_XT> llc = lcthptRepository.getThongTinXT_LopChuyenTHPT();
        Map<NguyenVongID, NguyenVong> nvMap = lnv.stream()
                .collect(Collectors.toMap(NguyenVong::getId, nv -> nv));

        // Xét Nguyện vọng 1 (chuyên) trước
        for (LopChuyenTHPT_XT lc : llc) {
            List<HocSinh_XT> ds = new ArrayList<>();
            for (HocSinh_XT hs : lhs) {
                NguyenVong nv1 = nvMap.get(new NguyenVongID(hs.getMaHS(), 1));
                if (nv1 != null) {
                    if (Objects.equals(nv1.getMaTHPT(), lc.getMaTHPT()) && Objects.equals(nv1.getMaLopChuyen(), lc.getMaLopChuyen())) {
                        if (hs.dieuKienChuyen()) {
                            ds.add(hs);
                        } else {
                            nv1.setKetQua("Hỏng");
                        }
                    }
                }
            }
            ds.sort(HocSinh_XT.soSanhByTongDiemChuyen().reversed());
            int soLuong = Math.min(lc.getChiTieu(), ds.size());
            if (soLuong > 0) {
                for (int i = 0; i < soLuong; i++) {
                    HocSinh_XT hs = ds.get(i);
                    hs.setNguyenVongTrungTuyen(1);
                    NguyenVong nv1 = nvMap.get(new NguyenVongID(hs.getMaHS(), 1));
                    nv1.setKetQua("Đậu");
                    lc.getDsHocSinhTrungTuyen().add(hs);
                }
                BigDecimal diemChuanChuyen = ds.get(soLuong-1).tinhTongDiemChuyen();
                BigDecimal diemChuyen = ds.get(soLuong-1).getDiemMonChuyen();
                for (int i = soLuong; i < ds.size(); i++) {
                    HocSinh_XT hs = ds.get(i);
                    if (Objects.equals(hs.tinhTongDiemChuyen(), diemChuanChuyen)
                            && Objects.equals(hs.getDiemMonChuyen(), diemChuyen)) {
                        hs.setNguyenVongTrungTuyen(1);
                        NguyenVong nv1 = nvMap.get(new NguyenVongID(hs.getMaHS(), 1));
                        nv1.setKetQua("Đậu");
                        lc.getDsHocSinhTrungTuyen().add(hs);
                    } else {
                        NguyenVong nv1 = nvMap.get(new NguyenVongID(hs.getMaHS(), 1));
                        nv1.setKetQua("Hỏng");
                    }
                }
            }
        }

        // Xét các nguyện vọng còn lại (từ 2 đến 5)
        for (int nv = 2; nv <= 5; nv++) {
            for (THPT_XT thpt : lthpt) {
                List<HocSinh_XT> ds = new ArrayList<>();
                for (HocSinh_XT hs : lhs) {
                    if (hs.getNguyenVongTrungTuyen() == 0) {
                        NguyenVong nvx = nvMap.get(new NguyenVongID(hs.getMaHS(), nv));
                        if (nvx != null) {
                            if (Objects.equals(nvx.getMaTHPT(), thpt.getMaTHPT())) {
                                if (hs.dieuKienChung()) {
                                    ds.add(hs);
                                } else {
                                    nvx.setKetQua("Hỏng");
                                }
                            }
                        }
                    }
                }
                ds.sort(HocSinh_XT.soSanhByTongDiemChung().reversed());
                int chiTieuConLai = thpt.getChiTieu() - thpt.getDsHocSinhTrungTuyen().size();
                int soLuong = Math.min(chiTieuConLai, ds.size());
                if (soLuong > 0) {
                    for (int i = 0; i < soLuong; i++) {
                        HocSinh_XT hs = ds.get(i);
                        hs.setNguyenVongTrungTuyen(nv);
                        NguyenVong nvx = nvMap.get(new NguyenVongID(hs.getMaHS(), nv));
                        nvx.setKetQua("Đậu");
                        thpt.getDsHocSinhTrungTuyen().add(hs);
                    }
                    BigDecimal diemChuan = ds.get(soLuong-1).tinhTongDiemChung();
                    for (int i = soLuong; i < ds.size(); i++) {
                        HocSinh_XT hs = ds.get(i);
                        if (Objects.equals(hs.tinhTongDiemChung(), diemChuan)) {
                            hs.setNguyenVongTrungTuyen(nv);
                            NguyenVong nvx = nvMap.get(new NguyenVongID(hs.getMaHS(), nv));
                            nvx.setKetQua("Đậu");
                            thpt.getDsHocSinhTrungTuyen().add(hs);
                        } else {
                            NguyenVong nvx = nvMap.get(new NguyenVongID(hs.getMaHS(), nv));
                            nvx.setKetQua("Hỏng");
                        }
                    }
                }
            }
        }
        nvRepository.saveAll(lnv);
    }
}
