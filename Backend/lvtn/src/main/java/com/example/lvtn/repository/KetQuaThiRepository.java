package com.example.lvtn.repository;

import com.example.lvtn.dto.HocSinh_XT;
import com.example.lvtn.dto.KetQuaTuyenSinhDTO;
import com.example.lvtn.model.KetQuaThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KetQuaThiRepository extends JpaRepository<KetQuaThi,String> {

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaTuyenSinhDTO(
            hs.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
            hs.maTHCS, thcs.tenTHCS, ts.maTHPT, thpt.tenTHPT,
            dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
            kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, nv.id.thuTu, nvthpt.tenTHPT
        )
        FROM KetQuaThi kqt
        JOIN ThiSinh ts ON kqt.maHS = ts.maHS
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        LEFT JOIN NguyenVong nv ON hs.maHS = nv.id.maHS
        LEFT JOIN THPT nvthpt ON nv.maTHPT = nvthpt.maTHPT
        WHERE ts.soBaoDanh = :soBaoDanh AND nv.ketQua = 'Đậu'
    """)
    KetQuaTuyenSinhDTO findBySBD(@Param("soBaoDanh") String soBaoDanh);

    @Query("""
        SELECT new com.example.lvtn.dto.HocSinh_XT(hs.maHS, ts.soBaoDanh, dtut.diemCong,
            dtkk.diemCong, dtkkc.diemCong, kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen,
            hs.diemTBMonChuyen, hs.tongDiemTBLop9, kqht.hocTap, kqht.renLuyen)
        FROM KetQuaThi kqt
        JOIN ThiSinh ts ON kqt.maHS = ts.maHS
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        LEFT JOIN KQHocTap kqht ON hs.maHS = kqht.id.maHS WHERE kqht.id.lop = 9
    """)
    List<HocSinh_XT> getBangDiemAllThiSinh();
}
