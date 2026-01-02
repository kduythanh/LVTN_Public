package com.example.lvtn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThiSinhDTO {

    private String maHS;

    private String soBaoDanh;

    private String phongThi;

    private String phongThiChuyen;

    private String hoVaChuLotHS;

    private String tenHS;

    private Boolean gioiTinh;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ngaySinh;

    private String noiSinh;

    private String maTHCS;

    private String tenTHCS;

    private String tenTHCSNgoaiTPCT;

    private String tenXaNgoaiTPCT;

    private String tenTinhNgoaiTPCT;

    private String maTHPT;

    private String tenTHPT;

    private Boolean thptChuyen;

    private String maLopChuyen;

    private String tenLopChuyen;

    private BigDecimal diemCongUuTien;

    private BigDecimal diemCongKhuyenKhich;

    private BigDecimal diemCongKhuyenKhichChuyen;

    private BigDecimal diemToan;

    private BigDecimal diemVan;

    private BigDecimal diemMonThu3;

    private BigDecimal diemMonChuyen;
}
