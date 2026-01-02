package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "THI_SINH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThiSinh {

    @Id
    @Column(name = "MA_HS")
    private String maHS;

    @Column(name = "MA_THPT")
    private String maTHPT;

    @Column(name = "MA_LOP_CHUYEN")
    private String maLopChuyen;

    @Column(name = "SO_BAO_DANH")
    private String soBaoDanh;

    @Column(name = "PHONG_THI")
    private String phongThi;

    @Column(name = "PHONG_THI_CHUYEN")
    private String phongThiChuyen;
}
