package com.example.lvtn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DAN_TOC")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanToc {

    @Id
    @Column(name = "MA_DT")
    private Integer maDT;

    @Column(name = "TEN_DT")
    private String tenDT;
}
