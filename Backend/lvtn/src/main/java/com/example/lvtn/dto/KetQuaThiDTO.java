package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaThiDTO {

    private String maHS;

    private String soBaoDanh;

    private String hoVaChuLotHS;

    private String tenHS;

    private BigDecimal diemToan;

    private BigDecimal diemVan;

    private BigDecimal diemMonThu3;

    private BigDecimal diemMonChuyen;

    private Boolean thptChuyen;
}
