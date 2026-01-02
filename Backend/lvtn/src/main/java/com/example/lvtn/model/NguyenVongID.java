package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class NguyenVongID {

    @Column(name = "MA_HS")
    private String maHS;

    @Column(name = "THU_TU")
    private Integer thuTu;
}
