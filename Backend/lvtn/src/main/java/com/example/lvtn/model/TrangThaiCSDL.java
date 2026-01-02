package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TRANG_THAI_CSDL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrangThaiCSDL {

    @Id
    @Column(name = "MA_TT")
    private Integer maTT;

    @Column(name = "TEN_TRANG_THAI")
    private String tenTrangThai;

    @Column(name = "KIEU_DU_LIEU")
    private String kieuDuLieu;

    @Column(name = "GIA_TRI_BOOLEAN")
    private Boolean giaTriBoolean;

    @Column(name = "GIA_TRI_TIMESTAMP")
    private LocalDateTime giaTriTimestamp;

    @Column(name = "GIA_TRI_CHUOI")
    private String giaTriChuoi;

    @Column(name = "MO_TA")
    private String moTa;

    @Column(name = "TG_CAP_NHAT")
    private LocalDateTime tgCapNhat;
}
