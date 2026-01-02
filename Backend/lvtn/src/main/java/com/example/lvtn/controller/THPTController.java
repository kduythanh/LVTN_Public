package com.example.lvtn.controller;

import com.example.lvtn.annotation.CheckCapNhatHoSo;
import com.example.lvtn.common.ApiRes;
import com.example.lvtn.dto.HocSinhDTO;
import com.example.lvtn.dto.HocSinhRequest;
import com.example.lvtn.dto.ImportHocSinhDTO;
import com.example.lvtn.dto.ThiSinhDTO;
import com.example.lvtn.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/thpt")
public class THPTController {
    private final HocSinhService hsService;
    private final ThiSinhService tsService;
    private final KetQuaThiService kqtService;

    public THPTController(HocSinhService hsService, ThiSinhService tsService, KetQuaThiService kqtService) {
        this.hsService = hsService;
        this.tsService = tsService;
        this.kqtService = kqtService;
    }

    // Tìm kiếm học sinh ngoại tỉnh dự thi tại trường THPT
    @Operation(
            summary = "Tìm kiếm học sinh ngoại tỉnh dự thi tại trường THPT",
            description = "Tìm kiếm học sinh ngoại tỉnh dự thi tại trường THPT",
            tags = { "canbo-thpt" })
    @GetMapping("/{maTHPT}/hocsinh")
    public ResponseEntity<ApiRes<List<HocSinhDTO>>> findHSNgoaiTinhByMaTHPT (@PathVariable String maTHPT){
        return ResponseEntity.ok(new ApiRes<>(200,
                "Tìm kiếm học sinh ngoại tỉnh của trường có mã số " + maTHPT + " thành công!",
                hsService.findHSNgoaiTinhBymaTHPT(maTHPT)));
    }

    // Tìm kiếm học sinh ngoại tỉnh dự thi tại trường THPT theo keyword
    @Operation(
            summary = "Tìm kiếm học sinh ngoại tỉnh dự thi tại trường THPT theo keyword",
            description = "Tìm kiếm học sinh ngoại tỉnh dự thi tại trường THPT theo keyword",
            tags = { "canbo-thpt" })
    @GetMapping("/{maTHPT}/hocsinh/search")
    public ResponseEntity<ApiRes<List<HocSinhDTO>>> findHSNgoaiTinhByMaTHPTAndKeyword (
            @PathVariable String maTHPT, @RequestParam String keyword){
        return ResponseEntity.ok(new ApiRes<>(200,
                "Tìm kiếm học sinh ngoại tỉnh của trường có mã số " + maTHPT + " thành công!",
                hsService.findHSNgoaiTinhByMaTHPTAndKeyword(maTHPT, keyword)));
    }

    // Tìm kiếm thí sinh dự thi tại trường THPT
    @Operation(
            summary = "Tìm kiếm thí sinh dự thi tại trường THPT",
            description = "Tìm kiếm thí sinh dự thi tại trường THPT",
            tags = { "canbo-thpt" })
    @GetMapping("/{maTHPT}/thisinh")
    public ResponseEntity<ApiRes<List<ThiSinhDTO>>> findTSByMaTHPT (@PathVariable String maTHPT){
        return ResponseEntity.ok(new ApiRes<>(200,
                "Tìm kiếm thí sinh trường THPT có mã số " + maTHPT + " thành công!",
                tsService.findTSByMaTHPT(maTHPT)));
    }

    // Tìm kiếm thí sinh dự thi tại trường THPT theo keyword
    @Operation(
            summary = "Tìm kiếm thí sinh dự thi tại trường THPT theo keyword",
            description = "Tìm kiếm thí sinh dự thi tại trường THPT theo keyword",
            tags = { "canbo-thpt" })
    @GetMapping("/{maTHPT}/thisinh/search")
    public ResponseEntity<ApiRes<List<ThiSinhDTO>>> findTSByMaTHPTAndKeyword (@PathVariable String maTHPT, @RequestParam String keyword){
        return ResponseEntity.ok(new ApiRes<>(200,
                "Tìm kiếm thí sinh trường THPT có mã số " + maTHPT + " và từ khóa " + keyword + " thành công!",
                tsService.findTSByMaTHPTAndKeyword(maTHPT, keyword)));
    }

    // Tìm thông tin học sinh
    @Operation(
            summary = "Tìm thông tin học sinh (Chức năng dành cho cán bộ THPT)",
            description = "Tìm thông tin học sinh",
            tags = { "canbo-thpt" })
    @GetMapping("/hocsinh/{maHS}")
    public ResponseEntity<ApiRes<HocSinhDTO>> getHocSinhbymaHS(@PathVariable String maHS) {
        HocSinhDTO result = hsService.findHSByMaHS(maHS);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm thông tin học sinh có mã số " + maHS + " thành công!", result));
    }

    // Thêm học sinh mới
    @Operation(
            summary = "Thêm học sinh mới",
            description = "Thêm học sinh mới vào hệ thống (Chức năng dành cho cán bộ THPT)",
            tags = { "canbo-thpt" })
    @PostMapping(value = "{maTHPT}/hocsinh", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<HocSinhDTO>> addHocSinh (@PathVariable String maTHPT, @RequestParam("data") String dataJson,
                                                          @RequestParam(value = "file", required = false) MultipartFile file) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        HocSinhRequest hsRequest = mapper.readValue(dataJson, HocSinhRequest.class);

        HocSinhDTO hsDTO = hsService.addHocSinh_THPT(maTHPT, hsRequest, file);
        return ResponseEntity.ok(
                new ApiRes<>(200, "Thêm học sinh mới có mã số " + hsDTO.getThongTinHS().getMaHS() + " thành công!", hsDTO));
    }

    // Thêm học sinh mới từ file import Excel
    @Operation(
            summary = "Thêm danh sách học sinh mới từ file Excel (Chức năng dành cho cán bộ THPT)",
            description = "Import danh sách học sinh mới từ Excel vào hệ thống",
            tags = { "canbo-thpt" })
    @PostMapping(value = "{maTHPT}/hocsinh/import", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<ImportHocSinhDTO>> importHocSinhTHPT (@PathVariable String maTHPT, @RequestParam("excelFile") MultipartFile excelFile,
                                                                   @RequestParam(value = "zipFile", required = false) MultipartFile zipFile) throws IOException {
        ImportHocSinhDTO result = hsService.importHocSinhTHPT(maTHPT, excelFile, zipFile);
        String zipStatus = (zipFile == null || zipFile.isEmpty()) ? " (Không có file ZIP ảnh)" : "";
        return ResponseEntity.ok(new ApiRes<>(200, "Import hoàn tất" + zipStatus + ". Số hồ sơ thành công: "
                + result.getSuccessList().size(), result));
    }

    // Cập nhật thông tin học sinh
    @Operation(
            summary = "Cập nhật thông tin học sinh",
            description = "Cập nhật thông tin học sinh (Chức năng dành cho cán bộ THPT)",
            tags = { "canbo-thpt" })
    @PatchMapping(value = "{maTHPT}/hocsinh/{maHS}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<HocSinhDTO>> updateHocSinh (@PathVariable String maTHPT, @PathVariable String maHS, @RequestPart("dataJson") String dataJson,
                                                             @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        HocSinhRequest hsRequest = mapper.readValue(dataJson, HocSinhRequest.class);
        HocSinhDTO hsDTO = hsService.updateHocSinh_RoleTHPT(maTHPT, maHS, hsRequest, file);
        return ResponseEntity.ok(new ApiRes<>(200, "Cập nhật thông tin học sinh có mã số " + maHS + " thành công!", hsDTO));
    }

    // Xóa học sinh
    @Operation(
            summary = "Xóa học sinh",
            description = "Xóa học sinh khỏi hệ thống (Chức năng dành cho cán bộ THPT)",
            tags = { "canbo-thpt" })
    @DeleteMapping("{maTHPT}/hocsinh/{maHS}")
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<Void>> deleteHocSinh (@PathVariable String maTHPT, @PathVariable String maHS){
        hsService.deleteHocSinh_RoleTHPT(maTHPT, maHS);
        return ResponseEntity.ok(new ApiRes<>(200, "Xóa thông tin học sinh có mã số " + maHS + " thành công!", null));
    }

    // Xóa toàn bộ học sinh (ngoại tỉnh) đăng ký NV cao nhất vào trường THPT
    @Operation(
            summary = "Xóa toàn bộ học sinh (ngoại tỉnh) dự thi vào trường THPT",
            description = "Xóa toàn bộ học sinh (ngoại tỉnh) dự thi vào trường THPT",
            tags = { "canbo-thpt" })
    @DeleteMapping("/{maTHPT}/hocsinh")
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<Void>> deleteHocSinhBymaTHPT (@PathVariable String maTHPT){
        hsService.deleteHocSinhNgoaiTinhBymaTHPT(maTHPT);
        return ResponseEntity.ok(new ApiRes<>(200, "Xóa tất cả học sinh của trường THPT có mã số " + maTHPT + " thành công!", null));
    }

    // Xuất danh sách học sinh (ngoại tỉnh) đăng ký tại trường THPT ra Excel
    @Operation(
            summary = "Xuất danh sách học sinh (ngoại tỉnh) đăng ký tại trường THPT ra Excel",
            description = "Xuất danh sách học sinh (ngoại tỉnh) đăng ký tại trường THPT ra Excel",
            tags = { "canbo-thpt" })
    @GetMapping("/{maTHPT}/hocsinh/export")
    public ResponseEntity<Resource> exportHocSinhNgoaiTinhTHPTToExcel(@PathVariable String maTHPT) throws IOException {
        byte[] data = hsService.exportHocSinhNgoaiTinhTHPTToExcel(maTHPT);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DanhSachHocSinhTHPT.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    // Xuất thông tin tài khoản học sinh (ngoại tỉnh) đăng ký tại trường THPT ra Excel
    @Operation(
            summary = "Xuất thông tin tài khoản học sinh (ngoại tỉnh) đăng ký tại trường THPT ra Excel",
            description = "Xuất thông tin tài khoản học sinh (ngoại tỉnh) đăng ký tại trường THPT ra Excel",
            tags = { "canbo-thpt" })
    @GetMapping("/{maTHPT}/hocsinh/taikhoan/export")
    public ResponseEntity<Resource> exportTKHocSinhNgoaiTinhToExcel(@PathVariable String maTHPT) throws IOException {
        byte[] data = hsService.exportTKHocSinhNgoaiTinhToExcel(maTHPT);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DanhSachTKHocSinhTHPT.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    // Xuất danh sách thí sinh dự thi tại trường THPT ra Excel
    @Operation(
            summary = "Xuất danh sách thí sinh, số báo danh, phòng thi ra Excel",
            description = "Xuất danh sách thí sinh, số báo danh, phòng thi ra Excel",
            tags = { "canbo-thpt" })
    @GetMapping("/{maTHPT}/thisinh/export")
    public ResponseEntity<Resource> exportDSThiSinhToExcel(@PathVariable String maTHPT) throws IOException {
        byte[] data = tsService.exportDSThiSinhToExcel(maTHPT);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DanhSachThiSinh.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    // Xuất danh sách thí sinh dự thi tại trường THPT ra Excel
    @Operation(
            summary = "Xuất bảng điểm hội đồng thi ra Excel",
            description = "Xuất bảng điểm hội đồng thi ra Excel",
            tags = { "canbo-thpt" })
    @GetMapping("/{maTHPT}/kqts/export")
    public ResponseEntity<Resource> exportBangDiemTHPTToExcel(@PathVariable String maTHPT) throws IOException {
        byte[] data = kqtService.exportKQTSByTHPT(maTHPT);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=KetQuaTuyenSinh_THPT.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
