package com.example.lvtn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaiKhoanToken {

    @Schema(description = "Token do JWT trả về khi đăng nhập")
    private String token;

    @Schema(description = "Thông tin về tài khoản")
    private TaiKhoanDTO user;
}
