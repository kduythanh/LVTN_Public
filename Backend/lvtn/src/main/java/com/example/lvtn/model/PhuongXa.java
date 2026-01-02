package com.example.lvtn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PHUONG_XA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhuongXa {

    @Id
    @Column(name = "MA_PHUONG_XA")
    private Integer maPhuongXa;

    @Column(name = "TEN_PHUONG_XA")
    private String tenPhuongXa;
}
