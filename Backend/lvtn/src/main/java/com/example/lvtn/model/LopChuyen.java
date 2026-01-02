package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LOP_CHUYEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopChuyen {

    @Id
    @Column(name = "MA_LOP_CHUYEN")
    private String maLopChuyen;

    @Column(name = "TEN_LOP_CHUYEN")
    private String tenLopChuyen;

    @Column(name = "MON_CHUYEN")
    private String monChuyen;
}
