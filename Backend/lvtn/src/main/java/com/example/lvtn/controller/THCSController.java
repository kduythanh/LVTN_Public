package com.example.lvtn.controller;

import com.example.lvtn.annotation.CheckCapNhatHoSo;
import com.example.lvtn.common.ApiRes;
import com.example.lvtn.dto.HocSinhDTO;
import com.example.lvtn.dto.HocSinhRequest;
import com.example.lvtn.dto.ImportHocSinhDTO;
import com.example.lvtn.dto.InitHocSinhRequest;
import com.example.lvtn.service.HocSinhService;
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
@RequestMapping("/api/thcs")
public class THCSController {
    private final HocSinhService hsService;
    public THCSController(HocSinhService hsService) {
        this.hsService = hsService;
    }

    @Operation(
            summary = "Lấy danh sách học sinh của trường THCS",
            description = "Lấy danh sách học sinh của trường THCS",
            tags = { "canbo-thcs" })
    @GetMapping("/{maTHCS}/hocsinh")
    public ResponseEntity<ApiRes<List<HocSinhDTO>>> findHSBymaTHCS (@PathVariable String maTHCS){
        return ResponseEntity.ok(new ApiRes<>(200,
                "Tìm danh sách học sinh của trường có mã số " + maTHCS + " thành công!",
                hsService.findHSByMaTHCS(maTHCS)));
    }

    @Operation(
            summary = "Tìm kiếm học sinh của trường THCS theo từ khóa",
            description = "Tìm kiếm học sinh của trường THCS theo từ khóa",
            tags = { "canbo-thcs" })
    @GetMapping("/{maTHCS}/hocsinh/search")
    public ResponseEntity<ApiRes<List<HocSinhDTO>>> findHSinTHCSByKeyword (@PathVariable String maTHCS, @RequestParam String keyword){
        return ResponseEntity.ok(new ApiRes<>(200,
                "Tìm danh sách học sinh của trường có mã số " + maTHCS + " với từ khóa " + keyword + " thành công!",
                hsService.findHSByMaTHCSAndKeyword(maTHCS,keyword)));
    }

    // Xuất danh sách toàn bộ học sinh đã đăng ký của trường THCS ra Excel
    @Operation(
            summary = "Xuất danh sách toàn bộ học sinh của trường THCS ra Excel",
            description = "Xuất danh sách toàn bộ học sinh của trường THCS ra Excel",
            tags = { "canbo-thcs" })
    @GetMapping("/{maTHCS}/hocsinh/export")
    public ResponseEntity<Resource> exportHocSinhTHCSToExcel(@PathVariable String maTHCS) throws IOException {
        byte[] data = hsService.exportHocSinhTHCSToExcel(maTHCS);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DanhSachHocSinhTHCS.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    // Xuất danh sách tài khoản học sinh đã đăng ký của trường THCS ra Excel
    @Operation(
            summary = "Xuất danh sách tài khoản học sinh của trường THCS ra Excel",
            description = "Xuất danh sách tài khoản học sinh của trường THCS ra Excel",
            tags = { "canbo-thcs" })
    @GetMapping("/{maTHCS}/hocsinh/taikhoan/export")
    public ResponseEntity<Resource> exportTKHocSinhTHCSToExcel(@PathVariable String maTHCS) throws IOException {
        byte[] data = hsService.exportTKHocSinhTHCSToExcel(maTHCS);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DanhSachHocSinhTHCS.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    // Tìm thông tin học sinh
    @Operation(
            summary = "Tìm thông tin học sinh (Chức năng dành cho cán bộ THCS)",
            description = "Tìm thông tin học sinh",
            tags = { "canbo-thcs" })
    @GetMapping("/hocsinh/{maHS}")
    public ResponseEntity<ApiRes<HocSinhDTO>> getHocSinhbymaHS(@PathVariable String maHS) {
        HocSinhDTO result = hsService.findHSByMaHS(maHS);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm thông tin học sinh có mã số " + maHS + " thành công!", result));
    }

    // Khởi tạo học sinh
    @Operation(
            summary = "Khởi tạo học sinh (Chức năng dành cho cán bộ THCS)",
            description = "Khởi tạo học sinh",
            tags = { "canbo-thcs" })
    @PostMapping(value = "{maTHCS}/hocsinh/init", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<HocSinhDTO>> initHocSinh (@PathVariable String maTHCS, @ModelAttribute InitHocSinhRequest hs)  {
        HocSinhDTO hsDTO = hsService.initHocSinh(maTHCS, hs);

        return ResponseEntity.ok(new ApiRes<>(200,
                "Khởi tạo hồ sơ học sinh có mã số " + hsDTO.getThongTinHS().getMaHS() + " thành công! Vui lòng export danh sách học sinh và cấp tài khoản, mật khẩu cho học sinh sử dụng!",
                hsDTO));
    }

    // Thêm học sinh mới
    @Operation(
            summary = "Thêm học sinh mới (Chức năng dành cho cán bộ THCS)",
            description = "Thêm học sinh mới vào hệ thống (đủ các thông tin cần thiết)",
            tags = { "canbo-thcs" })
    @PostMapping(value = "{maTHCS}/hocsinh", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<HocSinhDTO>> addHocSinh (@PathVariable String maTHCS, @RequestParam("data") String dataJson,
                                                          @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        HocSinhRequest hsRequest = mapper.readValue(dataJson, HocSinhRequest.class);

        HocSinhDTO hsDTO = hsService.addHocSinh_THCS(maTHCS, hsRequest, file);

        return ResponseEntity.ok(new ApiRes<>(200,
                "Thêm học sinh mới có mã số " + hsDTO.getThongTinHS().getMaHS() + " thành công!",
                hsDTO));
    }

    // Khởi tạo học sinh từ file import Excel
    @Operation(
            summary = "Khởi tạo học sinh từ file import Excel (Chức năng dành cho cán bộ THCS)",
            description = "Khởi tạo học sinh từ file import Excel",
            tags = { "canbo-thcs" })
    @PostMapping(value = "{maTHCS}/hocsinh/init/import", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<ImportHocSinhDTO>> importInitHocSinhTHCS (@PathVariable String maTHCS, @RequestParam("excelFile") MultipartFile excelFile){
        ImportHocSinhDTO result = hsService.importInitHocSinh(maTHCS, excelFile);
        return ResponseEntity.ok(new ApiRes<>(200, "Import hoàn tất. Số hồ sơ thành công: "
                + result.getSuccessList().size(), result));
    }

    // Thêm học sinh mới từ file import Excel
    @Operation(
            summary = "Thêm danh sách học sinh mới từ file Excel (Chức năng dành cho cán bộ THCS)",
            description = "Import danh sách học sinh mới từ Excel vào hệ thống",
            tags = { "canbo-thcs" })
    @PostMapping(value = "{maTHCS}/hocsinh/import", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<ImportHocSinhDTO>> importHocSinhTHCS (@PathVariable String maTHCS, @RequestParam("excelFile") MultipartFile excelFile,
                                                                   @RequestParam(value = "zipFile", required = false) MultipartFile zipFile) throws IOException {
        ImportHocSinhDTO result = hsService.importHocSinhTHCS(maTHCS, excelFile, zipFile);
        String zipStatus = (zipFile == null || zipFile.isEmpty()) ? " (Không có file ZIP ảnh)" : "";
        return ResponseEntity.ok(new ApiRes<>(200, "Import hoàn tất" + zipStatus + ". Số hồ sơ thành công: "
                + result.getSuccessList().size(), result));
    }

    // Cập nhật thông tin học sinh
    @Operation(
            summary = "Cập nhật thông tin học sinh (Chức năng dành cho cán bộ THCS)",
            description = "Cập nhật thông tin học sinh",
            tags = { "canbo-thcs" })
    @PatchMapping(value = "{maTHCS}/hocsinh/{maHS}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<HocSinhDTO>> updateHocSinh (@PathVariable String maTHCS, @PathVariable String maHS, @RequestPart("dataJson") String dataJson,
                                                             @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        HocSinhRequest hsRequest = mapper.readValue(dataJson, HocSinhRequest.class);
        HocSinhDTO hsDTO = hsService.updateHocSinh_RoleTHCS(maTHCS, maHS, hsRequest, file);
        return ResponseEntity.ok(new ApiRes<>(200, "Cập nhật thông tin học sinh có mã số " + maHS + " thành công!", hsDTO));
    }

    // Xóa học sinh
    @Operation(
            summary = "Xóa học sinh (Chức năng dành cho cán bộ THCS)",
            description = "Xóa học sinh khỏi hệ thống",
            tags = { "canbo-thcs" })
    @DeleteMapping("/{maTHCS}/hocsinh/{maHS}")
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<Void>> deleteHocSinh (@PathVariable String maTHCS, @PathVariable String maHS){
        hsService.deleteHocSinh_RoleTHCS(maTHCS, maHS);
        return ResponseEntity.ok(new ApiRes<>(200, "Xóa thông tin học sinh có mã số " + maHS + " thành công!", null));
    }

    // Xóa toàn bộ học sinh của trường THCS
    @Operation(
            summary = "Xóa toàn bộ học sinh của trường THCS",
            description = "Xóa toàn bộ học sinh của trường THCS",
            tags = { "canbo-thcs" })
    @DeleteMapping("/{maTHCS}/hocsinh")
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<Void>> deleteHocSinhBymaTHCS (@PathVariable String maTHCS){
        hsService.deleteHocSinhBymaTHCS(maTHCS);
        return ResponseEntity.ok(new ApiRes<>(200, "Xóa tất cả học sinh của trường THCS có mã số " + maTHCS + " thành công!", null));
    }
}
