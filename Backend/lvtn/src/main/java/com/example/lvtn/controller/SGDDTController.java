package com.example.lvtn.controller;

import com.example.lvtn.annotation.CheckCapNhatDiem;
import com.example.lvtn.annotation.CheckCapSoBaoDanh;
import com.example.lvtn.annotation.CheckXetTuyen;
import com.example.lvtn.common.ApiRes;
import com.example.lvtn.dto.ImportKetQuaThiDTO;
import com.example.lvtn.dto.KetQuaThiDTO;
import com.example.lvtn.dto.ThiSinhDTO;
import com.example.lvtn.model.KetQuaThi;
import com.example.lvtn.model.TrangThaiCSDL;
import com.example.lvtn.service.KetQuaThiService;
import com.example.lvtn.service.ThiSinhService;
import com.example.lvtn.service.TrangThaiCSDLService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sgddt")
public class SGDDTController {

    private final TrangThaiCSDLService ttcsdlService;
    private final ThiSinhService tsService;
    private final KetQuaThiService kqtService;

    public SGDDTController(TrangThaiCSDLService ttcsdlService, ThiSinhService tsService, KetQuaThiService kqtService) {
        this.ttcsdlService = ttcsdlService;
        this.tsService = tsService;
        this.kqtService = kqtService;
    }

    @Operation(summary = "Lấy danh sách trạng thái lưu trong CSDL", description = "Lấy danh sách trạng thái lưu trong CSDL", tags = { "sgddt" })
    @GetMapping("/trangthai")
    public ResponseEntity<ApiRes<List<TrangThaiCSDL>>> getTrangThai() {
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm trạng thái CSDL thành công!", ttcsdlService.findAll()));
    }

    @Operation(
            summary = "Lấy trạng thái theo mã trạng thái",
            description = "Lấy trạng thái theo mã trạng thái",
            tags = { "sgddt" })
    @GetMapping("/trangthai/{maTT}")
    public ResponseEntity<ApiRes<TrangThaiCSDL>> getTrangThaiByMaTT(@PathVariable Integer maTT) {
        TrangThaiCSDL tt = ttcsdlService.findById(maTT);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm trạng thái có mã " + maTT + " thành công!", tt));
    }

    @Operation(summary = "Cập nhật trạng thái hệ thống", description = "Cập nhật trạng thái hệ thống", tags = { "sgddt" })
    @PatchMapping("/trangthai/{maTT}")
    public ResponseEntity<ApiRes<Void>> updateTrangThai(@PathVariable Integer maTT, @RequestParam String kieuDuLieu, @RequestParam String newVal) {
        ttcsdlService.updateTrangThai(maTT, kieuDuLieu, newVal);
        return ResponseEntity.ok(new ApiRes<>(200, "Cập nhật trạng thái thành công!", null));
    }

    @Operation(summary = "Lấy danh sách thí sinh", description = "Lấy danh sách thí sinh", tags = { "sgddt" })
    @GetMapping("/thisinh")
    public ResponseEntity<ApiRes<List<ThiSinhDTO>>> getDSThiSinh() {
        List<ThiSinhDTO> dto = tsService.findAllTS();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm danh sách thí sinh thành công!", dto));
    }

    @Operation(summary = "Tìm kiếm thí sinh theo keyword", description = "Tìm kiếm thí sinh theo keyword", tags = { "sgddt" })
    @GetMapping("/thisinh/search")
    public ResponseEntity<ApiRes<List<ThiSinhDTO>>> searchThiSinhByKeyword(@RequestParam String keyword) {
        List<ThiSinhDTO> dto = tsService.findTSByKeyword(keyword);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm danh sách thí sinh với từ khóa " + keyword + " thành công!", dto));
    }

    @Operation(summary = "Lấy thí sinh theo SBD", description = "Lấy thí sinh theo SBD", tags = { "sgddt" })
    @GetMapping("/thisinh/{soBaoDanh}")
    public ResponseEntity<ApiRes<KetQuaThiDTO>> getThiSinhBySBD(@PathVariable String soBaoDanh) {
        if (soBaoDanh == null) throw new IllegalArgumentException("Thí sinh được chọn phải có SBD để cập nhật điểm!");
        KetQuaThiDTO result = tsService.findThiSinhBySoBaoDanh(soBaoDanh);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm thí sinh với số báo danh " + soBaoDanh + " thành công!", result));
    }

    @Operation(summary = "Cấp số báo danh, phòng thi cho các thí sinh", description = "Cấp số báo danh, phòng thi cho các thí sinh", tags = { "sgddt" })
    @PostMapping("/thisinh/capnhat")
    @CheckCapSoBaoDanh
    public ResponseEntity<ApiRes<List<ThiSinhDTO>>> updateSBDAndPhongThi() {
        List<ThiSinhDTO> res = tsService.updateSBDAndPhongThi();
        return ResponseEntity.ok(new ApiRes<>(200, "Cấp số báo danh, phòng thi cho các thí sinh thành công!", res));
    }

    @Operation(summary = "Cập nhật điểm thi của thí sinh", description = "Cập nhật điểm thi của thí sinh", tags = { "sgddt" })
    @PatchMapping(value = "/thisinh/kqthi/{soBaoDanh}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatDiem
    public ResponseEntity<ApiRes<KetQuaThi>> updateDiemThiSinh(@PathVariable String soBaoDanh, @ModelAttribute KetQuaThi ketQuaThi) {
        if (soBaoDanh == null) throw new IllegalArgumentException("Thí sinh được chọn phải có SBD để cập nhật điểm!");
        KetQuaThi kqThi = kqtService.updateKQThi(soBaoDanh, ketQuaThi);
        return ResponseEntity.ok(new ApiRes<>(200, "Cập nhật điểm cho thí sinh có số báo danh " + soBaoDanh + " thành công!", kqThi));
    }

    @Operation(summary = "Cập nhật điểm thi của thí sinh qua file Excel", description = "Cập nhật điểm thi của thí sinh qua file Excel", tags = { "sgddt" })
    @PatchMapping(value = "/thisinh/kqthi/import", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatDiem
    public ResponseEntity<ApiRes<ImportKetQuaThiDTO>> importKQThiFromExcel(@RequestParam("file") MultipartFile file) {
        ImportKetQuaThiDTO kqThi = kqtService.importKQThiFromExcel(file);
        return ResponseEntity.ok(new ApiRes<>(200, "Cập nhật điểm cho thí sinh hoàn tất. Thành công: " + kqThi.getSuccessList().size() + ", thất bại: " + kqThi.getErrorList().size(), kqThi));
    }

    @Operation(summary = "Xuất kết quả thi ra Excel", description = "Xuất kết quả thi ra Excel", tags = { "sgddt" })
    @GetMapping("/thisinh/kqthi/export")
    public ResponseEntity<Resource> exportKQThiToExcel() throws IOException {
        byte[] data = kqtService.exportKQThiToExcel();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=KetQuaThi.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @Operation(summary = "Xuất kết quả tuyển sinh ra Excel", description = "Xuất kết quả tuyển sinh ra Excel", tags = { "sgddt" })
    @GetMapping("/kqts/export")
    public ResponseEntity<Resource> exportKQTSToExcel() throws IOException {
        byte[] data = kqtService.exportKQTS();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=KetQuaTuyenSinh.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @Operation(summary = "Chạy hệ thống xét tuyển", description = "Chạy hệ thống xét tuyển", tags = { "sgddt" })
    @PatchMapping("/xettuyen")
    @CheckXetTuyen
    public ResponseEntity<ApiRes<Void>> xetTuyen() {
        kqtService.xetTuyen();
        return ResponseEntity.ok(new ApiRes<>(200, "Chạy hệ thống xét tuyển thành công!", null));
    }
}
