package com.example.lvtn.repository;

import com.example.lvtn.dto.LopChuyenTHPTDTO;
import com.example.lvtn.dto.LopChuyenTHPT_XT;
import com.example.lvtn.model.LopChuyenTHPT;
import com.example.lvtn.model.LopChuyenTHPTID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LopChuyenTHPTRepository extends JpaRepository<LopChuyenTHPT, LopChuyenTHPTID> {

    @Query("""
        SELECT new com.example.lvtn.dto.LopChuyenTHPT_XT(lc.id.maTHPT, lc.id.maLopChuyen, lc.chiTieu)
        FROM LopChuyenTHPT lc
    """)
    List<LopChuyenTHPT_XT> getThongTinXT_LopChuyenTHPT();

    @Query("""
        SELECT new com.example.lvtn.dto.LopChuyenTHPTDTO(lct.id.maTHPT, lct.id.maLopChuyen, lc.tenLopChuyen, lc.monChuyen) 
        FROM LopChuyenTHPT lct
        JOIN LopChuyen lc ON lct.id.maLopChuyen = lc.maLopChuyen
        WHERE lct.id.maTHPT = :maTHPT
    """)
    List<LopChuyenTHPTDTO> getAllLopChuyenTHPTByMaTHPT(@Param("maTHPT") String maTHPT);
}
