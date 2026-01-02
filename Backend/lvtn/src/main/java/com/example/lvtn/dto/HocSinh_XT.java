package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocSinh_XT {

    private String maHS;

    private String soBaoDanh;

    private BigDecimal diemUT;

    private BigDecimal diemKK;

    private BigDecimal diemKKChuyen;

    private BigDecimal diemToan;

    private BigDecimal diemVan;

    private BigDecimal diemMonThu3;

    private BigDecimal diemMonChuyen;

    private BigDecimal diemTBMonChuyen;

    private BigDecimal tongDiemTBLop9;

    private String hocTap9;

    private String renLuyen9;

    private Integer nguyenVongTrungTuyen;

    public HocSinh_XT(String maHS, String soBaoDanh, BigDecimal diemUT, BigDecimal diemKK, BigDecimal diemKKChuyen,
                      BigDecimal diemToan, BigDecimal diemVan, BigDecimal diemMonThu3, BigDecimal diemMonChuyen,
                      BigDecimal diemTBMonChuyen, BigDecimal tongDiemTBLop9, String hocTap9, String renLuyen9) {
        this.maHS = maHS;
        this.soBaoDanh = soBaoDanh;
        this.diemUT = diemUT;
        this.diemKK = diemKK;
        this.diemKKChuyen = diemKKChuyen;
        this.diemToan = diemToan;
        this.diemVan = diemVan;
        this.diemMonThu3 = diemMonThu3;
        this.diemMonChuyen = diemMonChuyen;
        this.diemTBMonChuyen = diemTBMonChuyen;
        this.tongDiemTBLop9 = tongDiemTBLop9;
        this.hocTap9 = hocTap9;
        this.renLuyen9 = renLuyen9;
        this.nguyenVongTrungTuyen = 0;
    }

    public BigDecimal tinhTongDiemChung() {
        return diemToan.add(diemVan).add(diemMonThu3).add(diemUT).add(diemKK);
    }

    public BigDecimal tinhTongDiemChuyen() {
        return diemToan.add(diemVan).add(diemMonThu3).add(diemMonChuyen.multiply(new BigDecimal("2.0"))).add(diemKKChuyen);
    }

    public boolean dieuKienChung() {
        if (diemToan == null || diemVan == null || diemMonThu3 == null) return false;
        return diemToan.compareTo(new BigDecimal("0.00")) > 0 && diemVan.compareTo(new BigDecimal("0.00")) > 0
                && diemMonThu3.compareTo(new BigDecimal("0.00")) > 0;
    }

    public boolean dieuKienChuyen() {
        if (diemToan == null || diemVan == null || diemMonThu3 == null || diemMonChuyen == null) return false;
        return diemToan.compareTo(new BigDecimal("5.00")) >= 0 && diemVan.compareTo(new BigDecimal("5.00")) >= 0
                && diemMonThu3.compareTo(new BigDecimal("5.00")) >= 0 && diemMonChuyen.compareTo(new BigDecimal("5.00")) >= 0;
    }

    public Integer tinhDiemKQHT(HocSinh_XT hocSinh_XT) {
        String hocTap9 = hocSinh_XT.hocTap9;
        String renLuyen9 = hocSinh_XT.renLuyen9;
        if (Objects.equals(hocTap9, "Tốt") && Objects.equals(renLuyen9, "Tốt")) return 10;
        if (Objects.equals(hocTap9, "Khá") && Objects.equals(renLuyen9, "Tốt")
                || Objects.equals(hocTap9, "Tốt") && Objects.equals(renLuyen9, "Khá")) return 9;
        if (Objects.equals(hocTap9, "Khá") && Objects.equals(renLuyen9, "Khá")) return 8;
        if (Objects.equals(hocTap9, "Đạt") && Objects.equals(renLuyen9, "Tốt")
                || Objects.equals(hocTap9, "Tốt") && Objects.equals(renLuyen9, "Đạt")) return 7;
        if (Objects.equals(hocTap9, "Đạt") && Objects.equals(renLuyen9, "Khá")
                || Objects.equals(hocTap9, "Khá") && Objects.equals(renLuyen9, "Đạt")) return 6;
        return 5;
    }

    public static Comparator<HocSinh_XT> soSanhByTongDiemChung() {
        return Comparator.comparing(HocSinh_XT::tinhTongDiemChung)
                .thenComparing(HocSinh_XT::tinhDiemKQHT);
    }

    public static Comparator<HocSinh_XT> soSanhByTongDiemChuyen() {
        return Comparator.comparing(HocSinh_XT::tinhTongDiemChuyen)
                .thenComparing(HocSinh_XT::getDiemMonChuyen)
                .thenComparing(HocSinh_XT::getDiemTBMonChuyen);
    }
}
