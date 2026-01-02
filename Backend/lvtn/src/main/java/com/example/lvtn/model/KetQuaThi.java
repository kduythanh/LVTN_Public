package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "KET_QUA_THI")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaThi {

    @Id
    @Column(name = "MA_HS")
    private String maHS;

    @Column(name = "DIEM_TOAN")
    private BigDecimal diemToan;

    @Column(name = "DIEM_VAN")
    private BigDecimal diemVan;

    @Column(name = "DIEM_MON_THU_3")
    private BigDecimal diemMonThu3;

    @Column(name = "DIEM_MON_CHUYEN")
    private BigDecimal diemMonChuyen;
}
