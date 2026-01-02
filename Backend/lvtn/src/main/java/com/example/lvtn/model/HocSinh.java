package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "HOC_SINH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocSinh {

    @Id
    @Column(name = "MA_HS")
    private String maHS;

    @Column(name = "HO_VA_CHU_LOT_HS")
    private String hoVaChuLotHS;

    @Column(name = "TEN_HS")
    private String tenHS;

    @Column(name = "GIOI_TINH")
    private Boolean gioiTinh;

    @Column(name = "NGAY_SINH")
    private LocalDate ngaySinh;

    @Column(name = "NOI_SINH")
    private String noiSinh;

    @Column(name = "MA_DT")
    private Integer maDT;

    @Column(name = "DIA_CHI_THUONG_TRU")
    private String diaChiThuongTru;

    @Column(name = "CHO_O_HIEN_NAY")
    private String choOHienNay;

    @Column(name = "MA_THCS")
    private String maTHCS;

    @Column(name = "NAM_TOT_NGHIEP_THCS")
    private Integer namTotNghiepTHCS;

    @Column(name = "MA_DTUT")
    private Integer maDTUT;

    @Column(name = "MA_DTKK")
    private Integer maDTKK;

    @Column(name = "MA_DTKK_CHUYEN")
    private Integer maDTKKChuyen;

    @Column(name = "NGOAI_NGU_DANG_HOC")
    private String ngoaiNguDangHoc;

    @Column(name = "NGOAI_NGU_DU_THI")
    private String ngoaiNguDuThi;

    @Column(name = "SO_DIEN_THOAI")
    private String soDienThoai;

    @Column(name = "DIEM_TB_MON_CHUYEN")
    private BigDecimal diemTBMonChuyen;

    @Column(name = "TONG_DIEM_TB_LOP_9")
    private BigDecimal tongDiemTBLop9;

    @Column(name = "ANH_DAI_DIEN")
    private String anhDaiDien;

    @Column(name = "TEN_THCS_NGOAI_TPCT")
    private String tenTHCSNgoaiTPCT;

    @Column(name = "TEN_XA_NGOAI_TPCT")
    private String tenXaNgoaiTPCT;

    @Column(name = "TEN_TINH_NGOAI_TPCT")
    private String tenTinhNgoaiTPCT;
}
