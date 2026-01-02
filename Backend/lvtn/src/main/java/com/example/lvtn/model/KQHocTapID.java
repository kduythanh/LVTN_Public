package com.example.lvtn.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KQHocTapID implements Serializable {
    @Column(name = "MA_HS")
    private String maHS;

    @Column(name = "LOP")
    private Integer lop;
}

