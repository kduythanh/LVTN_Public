package com.example.lvtn.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LOP_CHUYEN_THPT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopChuyenTHPT {

    @EmbeddedId
    @JsonUnwrapped
    private LopChuyenTHPTID id;

    @Column(name = "CHI_TIEU")
    private Integer chiTieu;
}
