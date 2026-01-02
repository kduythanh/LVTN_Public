package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguyenVongFullData {

    private String maHS;

    private Integer thuTu;

    private String maTHPT;

    private String tenTHPT;

    private String tenPhuongXaTHPT;

    private Boolean nv2B;

    private Boolean lopTiengPhap;

    private String maLopChuyen;

    private String tenLopChuyen;

    private String monChuyen;

    private String ketQua;
}
