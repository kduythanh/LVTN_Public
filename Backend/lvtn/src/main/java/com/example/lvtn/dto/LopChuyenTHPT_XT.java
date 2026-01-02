package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopChuyenTHPT_XT {
    private String maTHPT;
    private String maLopChuyen;
    private Integer chiTieu;
    private List<HocSinh_XT> dsHocSinhTrungTuyen = new ArrayList<>();

    public LopChuyenTHPT_XT(String maTHPT, String maLopChuyen, Integer chiTieu) {
        this.maTHPT = maTHPT;
        this.maLopChuyen = maLopChuyen;
        this.chiTieu = chiTieu;
        this.dsHocSinhTrungTuyen = new ArrayList<>();
    }
}
