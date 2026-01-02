package com.example.lvtn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TAI_KHOAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {

    @Id
    @Column(name = "TEN_TK")
    private String tenTK;

    @Column(name = "MAT_KHAU")
    private String matKhau;

    @Column(name = "MA_LOAI_TK")
    private Integer maLoaiTK;

    @Column(name = "SO_DINH_DANH")
    private String soDinhDanh;

    @Column(name = "HASH_MAT_KHAU")
    private String hashMatKhau;

    @Column(name = "DA_DOI_MAT_KHAU")
    private Boolean daDoiMatKhau;
}
