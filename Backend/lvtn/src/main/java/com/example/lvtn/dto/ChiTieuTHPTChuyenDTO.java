package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTieuTHPTChuyenDTO {

    private String maTHPT;

    private String tenTHPT;

    private String maLopChuyen;

    private String tenLopChuyen;

    private Integer chiTieu;

    private Long soLuongDangKy;
}
