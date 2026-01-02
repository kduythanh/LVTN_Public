package com.example.lvtn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanHSTHCS {

    @Schema(description = "Tên tài khoản")
    private String tenTK;

    @Schema(description = "Mật khẩu mặc định")
    private String matKhau;

    @Schema(description = "Họ và chữ lót HS")
    private String hoVaChuLotHS;

    @Schema(description = "Tên HS")
    private String tenHS;

    @Schema(description = "Giới tính")
    private Boolean gioiTinh;

    @Schema(description = "Ngày sinh")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ngaySinh;

    @Schema(description = "Nơi sinh")
    private String noiSinh;
}
