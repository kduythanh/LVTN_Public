package com.example.lvtn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanDangNhap {

    @Schema(description = "Tên tài khoản")
    private String tenTK;

    @Schema(format = "password", description = "Mật khẩu")
    private String matKhau;

    @Schema(description = "Token reCAPTCHA từ Google trả về")
    private String captchaToken;
}
