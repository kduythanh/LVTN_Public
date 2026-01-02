package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class THPT_XT {
    private String maTHPT;
    private Integer chiTieu;
    private List<HocSinh_XT> dsHocSinhTrungTuyen = new ArrayList<>();

    public THPT_XT(String maTHPT, Integer chiTieu) {
        this.maTHPT = maTHPT;
        this.chiTieu = chiTieu;
        this.dsHocSinhTrungTuyen = new ArrayList<>();
    }
}
