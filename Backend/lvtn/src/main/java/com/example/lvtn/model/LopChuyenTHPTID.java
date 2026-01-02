package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LopChuyenTHPTID implements Serializable {

    @Column(name = "MA_THPT")
    private String maTHPT;

    @Column(name = "MA_LOP_CHUYEN")
    private String maLopChuyen;
}
