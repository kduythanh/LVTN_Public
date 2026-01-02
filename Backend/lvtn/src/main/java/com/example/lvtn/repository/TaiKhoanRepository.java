package com.example.lvtn.repository;

import com.example.lvtn.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, String> {

    @Query("""
        SELECT tk
        FROM TaiKhoan tk
        ORDER BY tk.maLoaiTK, tk.tenTK
    """)
    List<TaiKhoan> findAllTaiKhoan();

    @Query("""
        SELECT tk
        FROM TaiKhoan tk
        WHERE tk.maLoaiTK = 2 OR tk.maLoaiTK = 3
        ORDER BY tk.maLoaiTK, tk.tenTK
    """)
    List<TaiKhoan> findTaiKhoanGV();

    @Query("""
        SELECT tk.tenTK, tk.maLoaiTK, tk.soDinhDanh,
        CASE
            WHEN tk.maLoaiTK = 0 THEN 'Admin'
            WHEN tk.maLoaiTK = 1 THEN 'Sở GD&ĐT TP Cần Thơ'
            WHEN tk.maLoaiTK = 2 THEN thcs.tenTHCS
            WHEN tk.maLoaiTK = 3 THEN thpt.tenTHPT
            WHEN tk.maLoaiTK = 4 THEN CONCAT(hs.hoVaChuLotHS, ' ', hs.tenHS)
        END as tenDinhDanh
        FROM TaiKhoan tk
        LEFT JOIN HocSinh hs ON tk.soDinhDanh = hs.maHS AND tk.maLoaiTK = 4
        LEFT JOIN THCS thcs ON tk.soDinhDanh = thcs.maTHCS AND tk.maLoaiTK = 2
        LEFT JOIN THPT thpt ON tk.soDinhDanh = thpt.maTHPT AND tk.maLoaiTK = 3
        WHERE
            LOWER(tk.tenTK) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(tk.soDinhDanh) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(
                CASE
                    WHEN tk.maLoaiTK = 0 THEN 'Admin'
                    WHEN tk.maLoaiTK = 1 THEN 'Sở GD&ĐT TP Cần Thơ'
                    WHEN tk.maLoaiTK = 2 THEN thcs.tenTHCS
                    WHEN tk.maLoaiTK = 3 THEN thpt.tenTHPT
                    WHEN tk.maLoaiTK = 4 THEN CONCAT(hs.hoVaChuLotHS, ' ', hs.tenHS)
                END
            ) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Object[]> findTKBykeyword(@Param("keyword") String keyword);
}
