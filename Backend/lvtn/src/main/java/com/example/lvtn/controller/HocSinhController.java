package com.example.lvtn.controller;

import com.example.lvtn.annotation.CheckCapNhatHoSo;
import com.example.lvtn.annotation.CheckCongBoKetQua;
import com.example.lvtn.common.ApiRes;
import com.example.lvtn.dto.HocSinhDTO;
import com.example.lvtn.dto.HocSinhRequest;
import com.example.lvtn.dto.KetQuaTuyenSinhDTO;
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

@RestController
@RequestMapping("/api/hocsinh")
public class HocSinhController {

    private final HocSinhService hsService;
    private final ThiSinhService tsService;
    public HocSinhController(HocSinhService hsService, ThiSinhService tsService) {
        this.hsService = hsService;
        this.tsService = tsService;
    }

    // Tìm thông tin học sinh
    @Operation(
            summary = "Tìm thông tin học sinh (Chức năng dành cho học sinh)",
            description = "Tìm thông tin học sinh",
            tags = { "hocsinh" })
    @GetMapping("/{maHS}")
    public ResponseEntity<ApiRes<HocSinhDTO>> getHocSinhbymaHS(@PathVariable String maHS) {
        HocSinhDTO result = hsService.findHSByMaHS(maHS);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm thông tin học sinh có mã số " + maHS + " thành công!", result));
    }

    // Cập nhật thông tin học sinh
    @Operation(
            summary = "Cập nhật thông tin học sinh (Chức năng dành cho học sinh)",
            description = "Cập nhật thông tin học sinh",
            tags = { "hocsinh" })
    @PatchMapping(value = "/{maHS}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CheckCapNhatHoSo
    public ResponseEntity<ApiRes<HocSinhDTO>> updateHocSinh (@PathVariable String maHS, @RequestPart("dataJson") String dataJson,
                                                             @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        HocSinhRequest hsRequest = mapper.readValue(dataJson, HocSinhRequest.class);
        HocSinhDTO hsDTO = hsService.updateHocSinh(maHS, hsRequest, file);
        return ResponseEntity.ok(new ApiRes<>(200, "Cập nhật thông tin học sinh có mã số " + maHS + " thành công!", hsDTO));
    }

    @Operation(
            summary = "Xuất phiếu đăng ký của học sinh ra file Word",
            description = "Xuất phiếu đăng ký của học sinh ra file Word",
            tags = { "hocsinh" })
    @GetMapping("/export-word/{maHS}")
    public ResponseEntity<Resource> exportHocSinhToWord(@PathVariable String maHS) throws Exception {
        byte[] data = hsService.exportHocSinhToWord(maHS);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PhieuDangKy_" + maHS + ".docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    // Tra cứu kết quả tuyển sinh theo mã HS
    @Operation(
            summary = "Tra cứu kết quả tuyển sinh theo mã HS",
            description = "Tra cứu kết quả tuyển sinh theo mã HS",
            tags = { "hocsinh" })
    @GetMapping("/ketquathi/{maHS}")
    @CheckCongBoKetQua
    public ResponseEntity<ApiRes<KetQuaTuyenSinhDTO>> findKetQuaTuyenSinhByMaHS(@PathVariable String maHS) {
        KetQuaTuyenSinhDTO kq = tsService.findKetQuaTuyenSinhByMaHS(maHS);
        return ResponseEntity.ok(new ApiRes<>(200, "Tra cứu kết quả thi thành công!", kq));
    }
}
