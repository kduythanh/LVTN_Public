package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRUONG_THPT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class THPT {

    @Id
    @Column(name = "MA_THPT")
    private String maTHPT;

    @Column(name = "TEN_THPT")
    private String tenTHPT;

    @Column(name = "DIA_CHI")
    private String diaChi;

    @Column(name = "MA_PHUONG_XA")
    private Integer maPhuongXa;

    @Column(name = "CHI_TIEU")
    private Integer chiTieu;

    @Column(name = "THPT_CHUYEN")
    private Boolean thptChuyen;

    @Column(name = "TS_NGOAI_TPCT")
    private Boolean tsNgoaiTPCT;

    @Column(name = "STT_THPT")
    private Integer sttTHPT;
}
