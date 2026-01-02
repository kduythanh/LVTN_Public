package com.example.lvtn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanRequest {

    @Schema(description = "Tên tài khoản")
    private String tenTK;

    @Schema(description = "Mã loại tài khoản")
    private Integer maLoaiTK;

    @Schema(description = "Số định danh")
    private String soDinhDanh;

}

