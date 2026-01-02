package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTieuTHPTDTO {

    private String maTHPT;

    private String tenTHPT;

    private String diaChi;

    private Integer chiTieu;

    private Long soLuongDangKy;
}
