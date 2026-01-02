package com.example.lvtn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
    @Schema(format = "password", description = "Mật khẩu cũ")
    private String oldPassword;

    @Schema(format = "password", description = "Mật khẩu mới")
    private String newPassword;

    @Schema(format = "password", description = "Xác nhận mật khẩu mới")
    private String confirmNewPassword;
}
