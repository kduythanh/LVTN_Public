package com.example.lvtn.service;

import com.example.lvtn.common.CommonUtil;
import com.example.lvtn.dto.KetQuaThiDTO;
import com.example.lvtn.dto.KetQuaTuyenSinhDTO;
import com.example.lvtn.dto.ThiSinhDTO;
import com.example.lvtn.model.KetQuaThi;
import com.example.lvtn.model.ThiSinh;
import com.example.lvtn.model.TrangThaiCSDL;
import com.example.lvtn.repository.KetQuaThiRepository;
import com.example.lvtn.repository.ThiSinhRepository;
import com.example.lvtn.repository.TrangThaiCSDLRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThiSinhService {
    private final ThiSinhRepository tsRepository;
    private final KetQuaThiRepository kqtRepository;
    private final CommonUtil commonUtil;
    public ThiSinhService(ThiSinhRepository tsRepository, KetQuaThiRepository kqtRepository, CommonUtil commonUtil) {
        this.tsRepository = tsRepository;
        this.kqtRepository = kqtRepository;
        this.commonUtil = commonUtil;
    }

    public List<ThiSinhDTO> findAllTS() {
        return tsRepository.findAllTS();
    }

    public List<ThiSinhDTO> findTSByKeyword(String keyword) {
        return tsRepository.findTSByKeyword(keyword);
    }

    public List<ThiSinhDTO> findTSByMaTHPT(String maTHPT) {
        return tsRepository.findTSByMaTHPT(maTHPT);
    }

    public List<ThiSinhDTO> findTSByMaTHPTAndKeyword(String maTHPT, String keyword) {
        return tsRepository.findTSByMaTHPTAndKeyword(maTHPT, keyword);
    }

    @Transactional
    public KetQuaThiDTO findThiSinhBySoBaoDanh(String soBaoDanh) {
        return tsRepository.findTSBySoBaoDanh(soBaoDanh);
    }

    @Transactional
    public void addListThiSinh_MonChung() {
        List<Object[]> lts = tsRepository.sortThiSinhChung();
        String currentMaTHPT = null;
        int sttTrongNhom = 0;  // đếm số HS trong cùng trường THPT
        int soPhong = 1;       // số phòng hiện tại
        int sttTrongPhong = 0; // đếm số HS trong phòng thi
        for (Object[] ts : lts) {
            String maHS = ts[0].toString();
            String maTHPT = ts[1].toString();
            String maMonChuyen = (ts[2] == null) ? null : ts[2].toString();
            if (!maTHPT.equals(currentMaTHPT)) {
                currentMaTHPT = maTHPT;
                sttTrongNhom = 0;
                soPhong = 1;
                sttTrongPhong = 0;
            }
            sttTrongNhom++;
            sttTrongPhong++;
            // Đánh số báo danh và thứ tự phòng thi chung cho thí sinh
            if (sttTrongPhong > 24) {
                sttTrongPhong = 1;
                soPhong++;
            }
            int sttTHPT = Integer.parseInt(ts[3].toString());
            String sbd = String.format("%02d%04d", sttTHPT, sttTrongNhom);
            String phongThi = String.format("%02d%02d", sttTHPT, soPhong);
            // Lưu vào CSDL
            tsRepository.save(new ThiSinh(maHS, maTHPT, maMonChuyen, sbd, phongThi, null));
            // Lưu vào bảng Kết quả thi
            KetQuaThi kqt = new KetQuaThi();
            kqt.setMaHS(maHS);
            kqtRepository.save(kqt);
        }
    }

    @Transactional
    public void addListThiSinh_MonChuyen() {
        List<Object[]> lts = tsRepository.sortThiSinhChuyen();
        String currentMaTHPT = null;
        String currentMaMonChuyen = null;
        int soPhong = 1;       // số phòng hiện tại
        int sttTrongPhong = 0; // đếm số HS trong phòng thi
        for (Object[] ts : lts) {
            String maHS = ts[0].toString();
            String maTHPT = ts[1].toString();
            String maMonChuyen = ts[2].toString();
            if (!maTHPT.equals(currentMaTHPT)) {
                currentMaTHPT = maTHPT;
                currentMaMonChuyen = maMonChuyen;
                soPhong = 1;
                sttTrongPhong = 0;
            } else if (!maMonChuyen.equals(currentMaMonChuyen)) {
                currentMaMonChuyen = maMonChuyen;
                soPhong++;
                sttTrongPhong = 0;
            }
            sttTrongPhong++;
            // Đánh số báo danh và thứ tự phòng thi chuyên cho thí sinh
            if (sttTrongPhong > 24) {
                sttTrongPhong = 1;
                soPhong++;
            }
            int sttTHPT = Integer.parseInt(ts[3].toString());
            String phongThiChuyen = String.format("%02d%02d", sttTHPT, soPhong);
            // Lưu vào CSDL
            Optional<ThiSinh> t = tsRepository.findById(maHS);
            if (t.isPresent()) {
                ThiSinh currentTS = t.get();
                currentTS.setPhongThiChuyen(phongThiChuyen);
                tsRepository.save(currentTS);
            }
        }
    }

    @Transactional
    public void resetThiSinhData() {
        tsRepository.resetThiSinhData();
    }

    @Transactional
    public byte[] exportDSThiSinhToExcel(String maTHPT) throws IOException {
        List<ThiSinhDTO> ts = findTSByMaTHPT(maTHPT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Map<String, List<ThiSinhDTO>> byPhongThi = ts.stream()
                .collect(Collectors.groupingBy(t -> {
                    String p = t.getPhongThi();
                    return (p == null || p.isBlank()) ? "Chưa xếp phòng" : p;
                }));
        Map<String, List<ThiSinhDTO>> byPhongThiChuyen = ts.stream()
                .filter(t -> t.getPhongThiChuyen() != null && !t.getPhongThiChuyen().isBlank())
                .collect(Collectors.groupingBy(ThiSinhDTO::getPhongThiChuyen));
        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle headerStyle = commonUtil.createHeaderStyle(workbook);
            String[] headers = { "MSHS", "Số báo danh", "Phòng thi", "Phòng thi chuyên", "Họ và chữ lót", "Tên", "Nữ", "Ngày sinh", "Nơi sinh", "Trường THCS"};
            // Tạo sheet tổng hợp
            Sheet allSheet = workbook.createSheet("Danh sách");
            writeSheet(headers, headerStyle, allSheet, ts, formatter);

            // Tạo sheet phòng thi chung
            Map<String, List<ThiSinhDTO>> groupPhongThi =
                    ts.stream()
                            .filter(t -> t.getPhongThi() != null && !t.getPhongThi().isBlank())
                            .collect(Collectors.groupingBy(ThiSinhDTO::getPhongThi));

            List<String> sortedPhongThi = new ArrayList<>(groupPhongThi.keySet());
            sortedPhongThi.sort(Comparator.naturalOrder());
            for (String phong : sortedPhongThi) {
                Sheet s = workbook.createSheet(phong);
                writeSheet(headers, headerStyle, s, groupPhongThi.get(phong), formatter);
            }

            List<ThiSinhDTO> chuaXepPhong = ts.stream()
                    .filter(t -> t.getPhongThi() == null || t.getPhongThi().isBlank())
                    .toList();

            if (!chuaXepPhong.isEmpty()) {
                Sheet s = workbook.createSheet("Chưa xếp phòng");
                writeSheet(headers, headerStyle, s, chuaXepPhong, formatter);
            }

            // Tạo sheet phòng thi chuyên
            Map<String, List<ThiSinhDTO>> groupPhongChuyen =
                    ts.stream()
                            .filter(t -> t.getPhongThiChuyen() != null && !t.getPhongThiChuyen().isBlank())
                            .collect(Collectors.groupingBy(ThiSinhDTO::getPhongThiChuyen));

            List<String> sortedPhongChuyen = new ArrayList<>(groupPhongChuyen.keySet());
            sortedPhongChuyen.sort(Comparator.naturalOrder());
            for (String pc : sortedPhongChuyen) {
                Sheet s = workbook.createSheet("Chuyên-" + pc);
                writeSheet(headers, headerStyle, s, groupPhongChuyen.get(pc), formatter);
            }

            // Xuất ra ByteArrayInputStream để trả về API
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
            return out.toByteArray();
        }
    }

    private void writeSheet(
            String[] headers,
            CellStyle headerStyle,
            Sheet sheet,
            List<ThiSinhDTO> data,
            DateTimeFormatter formatter
    ) {
        // header
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // body
        int rowIdx = 1;
        for (ThiSinhDTO t : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(t.getMaHS());
            row.createCell(1).setCellValue(t.getSoBaoDanh());
            row.createCell(2).setCellValue(t.getPhongThi());
            row.createCell(3).setCellValue(t.getPhongThiChuyen());
            row.createCell(4).setCellValue(t.getHoVaChuLotHS());
            row.createCell(5).setCellValue(t.getTenHS());
            row.createCell(6).setCellValue(commonUtil.convertBooleanToString(t.getGioiTinh()));

            LocalDate ngaySinh = t.getNgaySinh();
            row.createCell(7).setCellValue(ngaySinh != null ? ngaySinh.format(formatter) : "");

            row.createCell(8).setCellValue(t.getNoiSinh());
            row.createCell(9).setCellValue(t.getTenTHCS());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }


    @Transactional
    public List<ThiSinhDTO> updateSBDAndPhongThi() {
        resetThiSinhData();
        addListThiSinh_MonChung(); // Cập nhật SBD, phòng thi môn chung
        addListThiSinh_MonChuyen(); // Cập nhật phòng thi môn chuyên
        return findAllTS();
    }

    @Transactional
    public List<KetQuaTuyenSinhDTO> getKetQuaTuyenSinh(String maTHPT, int type, String keyword) {
        List<KetQuaTuyenSinhDTO> ts = new ArrayList<>();
        if (type == 1) {
            KetQuaTuyenSinhDTO res = tsRepository.findKQTSByMaTHPTAndSoBaoDanh(maTHPT, keyword);
            if (res != null) ts.add(res);
        } else if (type == 2) {
            KetQuaTuyenSinhDTO res = tsRepository.findKQTSByMaTHPTAndMaHS(maTHPT, keyword);
            if (res != null) ts.add(res);
        } else {
            List<KetQuaTuyenSinhDTO> res = tsRepository.findKQTSByMaTHPTAndTenHS(maTHPT, keyword);
            if (res != null) ts.addAll(res);
        }
        return ts;
    }

    @Transactional
    public KetQuaTuyenSinhDTO findKetQuaTuyenSinhBySBD(String soBaoDanh) {
        KetQuaTuyenSinhDTO res = tsRepository.findKQTSBySoBaoDanh(soBaoDanh);
        if (res == null) throw new EntityNotFoundException("Không tìm thấy thí sinh với số báo danh " + soBaoDanh);
        return res;
    }

    @Transactional
    public KetQuaTuyenSinhDTO findKetQuaTuyenSinhByMaHS(String maHS) {
        KetQuaTuyenSinhDTO res = tsRepository.findKQTSByMaHS(maHS);
        if (res == null) throw new EntityNotFoundException("Không tìm thấy thí sinh với số báo danh " + maHS);
        return res;
    }
}
