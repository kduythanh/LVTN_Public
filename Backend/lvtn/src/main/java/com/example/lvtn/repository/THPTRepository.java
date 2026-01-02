package com.example.lvtn.repository;

import com.example.lvtn.dto.ChiTieuTHPTChuyenDTO;
import com.example.lvtn.dto.ChiTieuTHPTDTO;
import com.example.lvtn.dto.THPT_XT;
import com.example.lvtn.model.THPT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface THPTRepository extends JpaRepository<THPT, String> {

    @Query("""
        SELECT new com.example.lvtn.dto.THPT_XT(thpt.maTHPT, thpt.chiTieu)
        FROM THPT thpt
    """)
    List<THPT_XT> getThongTinXT_THPT();

    @Query("""
        SELECT new com.example.lvtn.dto.ChiTieuTHPTDTO(thpt.maTHPT, thpt.tenTHPT, thpt.diaChi, thpt.chiTieu, COUNT(nv.id.maHS))
        FROM THPT thpt
        LEFT JOIN NguyenVong nv ON nv.maTHPT = thpt.maTHPT AND nv.id.thuTu = (
            SELECT MIN(nv2.id.thuTu) FROM NguyenVong nv2 WHERE nv2.id.maHS = nv.id.maHS
        )
        GROUP BY thpt.maTHPT
        ORDER BY thpt.maTHPT
    """)
    List<ChiTieuTHPTDTO> findDSTHPTAndChiTieu();

    @Query("""
        SELECT new com.example.lvtn.dto.ChiTieuTHPTChuyenDTO(thpt.maTHPT, thpt.tenTHPT, lc.maLopChuyen, lc.tenLopChuyen, lct.chiTieu, COUNT(nv.id.maHS))
        FROM THPT thpt
        JOIN LopChuyenTHPT lct ON thpt.maTHPT = lct.id.maTHPT
        JOIN LopChuyen lc ON lct.id.maLopChuyen = lc.maLopChuyen
        LEFT JOIN NguyenVong nv ON nv.maTHPT = thpt.maTHPT AND nv.maLopChuyen = lct.id.maLopChuyen AND nv.id.thuTu = 1
        WHERE thpt.thptChuyen = TRUE
        GROUP BY thpt.maTHPT, thpt.tenTHPT, lc.maLopChuyen, lc.tenLopChuyen, lct.chiTieu
        ORDER BY thpt.maTHPT, lc.maLopChuyen
    """)
    List<ChiTieuTHPTChuyenDTO> findDSTHPTChuyenAndChiTieu();
}
