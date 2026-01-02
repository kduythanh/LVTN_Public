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
@Table(name = "DTKK_CHUYEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTKKChuyen {

    @Id
    @Column(name = "MA_DTKK_CHUYEN")
    private Integer maDTKKChuyen;

    @Column(name = "TEN_DTKK_CHUYEN")
    private String tenDTKKChuyen;

    @Column(name = "DIEM_CONG")
    private BigDecimal diemCong;
}
