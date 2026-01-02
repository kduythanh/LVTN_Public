package com.example.lvtn.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NGUYEN_VONG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguyenVong {

    @EmbeddedId
    @JsonUnwrapped
    private NguyenVongID id;

    @Column(name = "MA_THPT")
    private String maTHPT;

    @Column(name = "NV_2B")
    private Boolean nv2B;

    @Column(name = "LOP_TIENG_PHAP")
    private Boolean lopTiengPhap;

    @Column(name = "MA_LOP_CHUYEN")
    private String maLopChuyen;

    @Column(name = "KET_QUA")
    private String ketQua;
}
