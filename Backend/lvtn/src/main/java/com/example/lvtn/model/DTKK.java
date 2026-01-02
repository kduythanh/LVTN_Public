package com.example.lvtn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "DTKK")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTKK {

    @Id
    @Column(name = "MA_DTKK")
    private Integer maDTKK;

    @Column(name = "TEN_DTKK")
    private String tenDTKK;

    @Column(name = "DIEM_CONG")
    private BigDecimal diemCong;
}
