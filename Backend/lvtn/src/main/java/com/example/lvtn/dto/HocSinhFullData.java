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
public class HocSinhFullData {

    private String maHS;

    private String hoVaChuLotHS;

    private String tenHS;

    private Boolean gioiTinh;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ngaySinh;

    private String noiSinh;

    private Integer maDT;

    private String tenDT;

    private String diaChiThuongTru;

    private String choOHienNay;

    private String maTHCS;

    private String tenTHCS;

    private String tenPhuongXaTHCS;

    private Integer namTotNghiepTHCS;

    private Integer maDTUT;

    private String tenDTUT;

    private BigDecimal diemCongDTUT;

    private Integer maDTKK;

    private String tenDTKK;

    private BigDecimal diemCongDTKK;

    private Integer maDTKKChuyen;

    private String tenDTKKChuyen;

    private BigDecimal diemCongDTKKChuyen;

    private String ngoaiNguDangHoc;

    private String ngoaiNguDuThi;

    private String soDienThoai;

    private BigDecimal diemTBMonChuyen;

    private BigDecimal tongDiemTBLop9;

    private String anhDaiDien;

    private String tenTHCSNgoaiTPCT;

    private String tenXaNgoaiTPCT;

    private String tenTinhNgoaiTPCT;
}
