package com.example.lvtn.controller;

import com.example.lvtn.common.ApiRes;
import com.example.lvtn.dto.ImportAccountDTO;
import com.example.lvtn.dto.ResetPasswordDTO;
import com.example.lvtn.dto.TaiKhoanDTO;
import com.example.lvtn.dto.TaiKhoanRequest;
import com.example.lvtn.service.TaiKhoanService;
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
@RequestMapping("/api/admin")
public class AdminController {
    private final TaiKhoanService tkService;

    public AdminController(TaiKhoanService tkService) {
        this.tkService = tkService;
    }

    // Lấy danh sách toàn bộ tài khoản (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Lấy danh sách tài khoản",
            description = "Lấy toàn bộ danh sách tài khoản",
            tags = { "admin" })
    @GetMapping("/account")
    public ResponseEntity<ApiRes<List<TaiKhoanDTO>>> getAllTaiKhoan() {
        List<TaiKhoanDTO> tkDTO = tkService.findAllTaiKhoan();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm tài khoản thành công!", tkDTO));
    }

    // Tìm tài khoản thông qua keyword
    @Operation(
            summary = "Tìm/lọc tài khoản",
            description = "Tìm danh sách tài khoản bằng keyword",
            tags = { "admin" })
    @GetMapping("/account/search")
    public ResponseEntity<ApiRes<List<TaiKhoanDTO>>> findTaiKhoanBykeyword(@RequestParam(required = false) String keyword) {
        List<TaiKhoanDTO> tkDTO = tkService.findTaiKhoanBykeyword(keyword);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm tài khoản thành công! Tìm thấy " + tkDTO.size() + " tài khoản thỏa mãn", tkDTO));
    }

    // Xuất danh sách toàn bộ tài khoản ra Excel (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Xuất danh sách tài khoản ra Excel",
            description = "Xuất danh sách toàn bộ tài khoản ra Excel",
            tags = { "admin" })
    @GetMapping("/account/export")
    public ResponseEntity<Resource> exportAccounts() throws IOException {
        byte[] data = tkService.exportTaiKhoanToExcel();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DanhSachTaiKhoan.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    // Xuất danh sách toàn bộ tài khoản GV ra Excel (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Xuất danh sách tài khoản GV ra Excel",
            description = "Xuất danh sách toàn bộ tài khoản GV ra Excel",
            tags = { "admin" })
    @GetMapping("/account/canbo/export")
    public ResponseEntity<Resource> exportGVAccounts() throws IOException {
        byte[] data = tkService.exportTaiKhoanGVToExcel();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DanhSachTaiKhoanGV.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    // Tạo tài khoản mới (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Tạo tài khoản",
            description = "Quản trị viên tạo tài khoản mới",
            tags = { "admin" })
    @PostMapping(value = "/account", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiRes<TaiKhoanDTO>> createAccount(@ModelAttribute TaiKhoanRequest tkRequest) {
        TaiKhoanDTO tk = tkService.taoTaiKhoan(tkRequest);
        return ResponseEntity.ok(new ApiRes<>(200, "Tạo thành công tài khoản: " + tk.getTenTK(), tk));
    }

    // Import danh sách tài khoản từ Excel (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Import file Excel để tạo tài khoản",
            description = "Quản trị viên tạo tài khoản bằng cách import file Excel",
            tags = { "admin" })
    @PostMapping(value = "/account/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiRes<ImportAccountDTO>> importAccounts(@RequestParam("file") MultipartFile file) {
        ImportAccountDTO result = tkService.importAccountsFromExcel(file);
        return ResponseEntity.ok(new ApiRes<>(200, "Import hoàn tất. Thành công: "
                + result.getSuccessList().size() + ", Lỗi: " + result.getErrorList().size(), result));
    }

    // Xóa tài khoản (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Xóa tài khoản",
            description = "Quản trị viên xóa tài khoản",
            tags = { "admin" })
    @DeleteMapping("/account/{tenTK}")
    public ResponseEntity<ApiRes<Void>> deleteAccount(@PathVariable String tenTK) {
        tkService.deleteTaiKhoan(tenTK);
        return ResponseEntity.ok(new ApiRes<>(200, "Xóa thành công tài khoản: " + tenTK, null));
    }

    // Cấp lại mật khẩu cho tài khoản (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Cấp lại mật khẩu cho tài khoản",
            description = "Quản trị viên cấp lại mật khẩu cho tài khoản",
            tags = { "admin" })
    @PatchMapping("/account/reset/{tenTK}")
    public ResponseEntity<ApiRes<ResetPasswordDTO>> resetPassword(@PathVariable String tenTK) {
        ResetPasswordDTO result = tkService.resetPassword(tenTK);
        return ResponseEntity.ok(new ApiRes<>(200, "Cấp lại mật khẩu cho tài khoản " + tenTK + " thành công! Mật khẩu mới là: " + result.getNewPassword(), null));
    }

}
