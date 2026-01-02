package com.example.lvtn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitHocSinhRequest {

    private String maHS;

    private String hoVaChuLotHS;

    private String tenHS;

    private Boolean gioiTinh;

    private LocalDate ngaySinh;

    private String noiSinh;

    private String maTHCS;
}
