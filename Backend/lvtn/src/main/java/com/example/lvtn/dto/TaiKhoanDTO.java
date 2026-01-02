package com.example.lvtn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanDTO {

    @Schema(description = "Tên tài khoản")
    private String tenTK;

    @Schema(description = "Mã loại tài khoản")
    private Integer maLoaiTK;

    @Schema(description = "Số định danh")
    private String soDinhDanh;

    @Schema(description = "Tên định danh (tên trường/ tên học sinh/ admin/ hội đồng tuyển sinh)")
    private String tenDinhDanh;
}

