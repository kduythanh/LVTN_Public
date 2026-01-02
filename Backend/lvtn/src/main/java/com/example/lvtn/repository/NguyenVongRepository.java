package com.example.lvtn.repository;

import com.example.lvtn.dto.NguyenVongFullData;
import com.example.lvtn.model.NguyenVong;
import com.example.lvtn.model.NguyenVongID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NguyenVongRepository extends JpaRepository<NguyenVong, NguyenVongID> {

    @Query("""
        SELECT new com.example.lvtn.dto.NguyenVongFullData(nv.id.maHS, nv.id.thuTu, nv.maTHPT, thpt.tenTHPT, px.tenPhuongXa,
        nv.nv2B, nv.lopTiengPhap, nv.maLopChuyen, lc.tenLopChuyen, lc.monChuyen, nv.ketQua)
        FROM NguyenVong nv
        LEFT JOIN THPT thpt ON nv.maTHPT = thpt.maTHPT
        LEFT JOIN PhuongXa px ON thpt.maPhuongXa = px.maPhuongXa
        LEFT JOIN LopChuyen lc ON nv.maLopChuyen = lc.maLopChuyen
        WHERE nv.id.maHS = :maHS
        ORDER BY nv.id.thuTu
    """)
    List<NguyenVongFullData> findNguyenVongByMaHS(@Param("maHS") String maHS);

    void deleteById_MaHS(String maHS);

    @Query("""
        SELECT nv.id.maHS, nv.maTHPT, thpt.sttTHPT
        FROM NguyenVong nv
        JOIN HocSinh hs ON nv.id.maHS = hs.maHS
        JOIN THPT thpt ON nv.maTHPT = thpt.maTHPT
        WHERE nv.id.thuTu = (
                SELECT MIN(nv2.id.thuTu)
                FROM NguyenVong nv2
                WHERE nv2.id.maHS = nv.id.maHS
            )
        ORDER BY nv.maTHPT, hs.tenHS, hs.hoVaChuLotHS
    """)
    List<Object[]> findNVMax_MonChung();

    @Query("""
        SELECT nv.id.maHS, nv.maTHPT, nv.maLopChuyen, thpt.sttTHPT
        FROM NguyenVong nv
        JOIN HocSinh hs ON nv.id.maHS = hs.maHS
        JOIN THPT thpt ON nv.maTHPT = thpt.maTHPT
        WHERE nv.id.thuTu = (
                SELECT MIN(nv2.id.thuTu)
                FROM NguyenVong nv2
                WHERE nv2.id.maHS = nv.id.maHS
            )
        AND nv.maLopChuyen IS NOT NULL
        ORDER BY nv.maTHPT, nv.maLopChuyen, hs.tenHS, hs.hoVaChuLotHS
    """)
    List<Object[]> findNVMax_MonChuyen();
}
