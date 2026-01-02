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
@Table(name = "KQ_HOC_TAP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KQHocTap {

    @EmbeddedId
    @JsonUnwrapped
    private KQHocTapID id;

    @Column(name = "HOC_TAP")
    private String hocTap;

    @Column(name = "REN_LUYEN")
    private String renLuyen;
}
