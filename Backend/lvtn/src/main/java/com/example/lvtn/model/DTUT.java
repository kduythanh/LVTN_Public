package com.example.lvtn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "DTUT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTUT {

    @Id
    @Column(name = "MA_DTUT")
    private Integer maDTUT;

    @Column(name = "TEN_DTUT")
    private String tenDTUT;

    @Column(name = "DIEM_CONG")
    private BigDecimal diemCong;

}
