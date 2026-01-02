package com.example.lvtn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRUONG_THCS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class THCS {

    @Id
    @Column(name = "MA_THCS")
    private String maTHCS;

    @Column(name = "TEN_THCS")
    private String tenTHCS;

    @Column(name = "MA_PHUONG_XA")
    private Integer maPhuongXa;
}
