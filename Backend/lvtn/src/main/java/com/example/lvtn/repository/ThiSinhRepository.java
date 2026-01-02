package com.example.lvtn.repository;

import com.example.lvtn.dto.KetQuaThiDTO;
import com.example.lvtn.dto.KetQuaTuyenSinhDTO;
import com.example.lvtn.dto.ThiSinhDTO;
import com.example.lvtn.model.ThiSinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ThiSinhRepository extends JpaRepository<ThiSinh, String> {

    @Query("""
        SELECT new com.example.lvtn.dto.ThiSinhDTO(ts.maHS, ts.soBaoDanh, ts.phongThi, ts.phongThiChuyen,
        hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh, hs.maTHCS, thcs.tenTHCS,
        hs.tenTHCSNgoaiTPCT, hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT, ts.maTHPT, thpt.tenTHPT, thpt.thptChuyen,
        ts.maLopChuyen, lc.tenLopChuyen, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        LEFT JOIN LopChuyen lc ON ts.maLopChuyen = lc.maLopChuyen
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        LEFT JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        ORDER BY thpt.sttTHPT, ts.maLopChuyen, hs.tenHS, hs.hoVaChuLotHS
    """)
    List<ThiSinhDTO> findAllTS();

    @Query("""
        SELECT new com.example.lvtn.dto.ThiSinhDTO(ts.maHS, ts.soBaoDanh, ts.phongThi, ts.phongThiChuyen,
        hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh, hs.maTHCS, thcs.tenTHCS,
        hs.tenTHCSNgoaiTPCT, hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT, ts.maTHPT, thpt.tenTHPT, thpt.thptChuyen,
        ts.maLopChuyen, lc.tenLopChuyen, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        LEFT JOIN LopChuyen lc ON ts.maLopChuyen = lc.maLopChuyen
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        LEFT JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        WHERE (
                LOWER(ts.maHS) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(ts.soBaoDanh) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(CONCAT(hs.hoVaChuLotHS, ' ', hs.tenHS)) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
        ORDER BY thpt.sttTHPT, ts.maLopChuyen, hs.tenHS, hs.hoVaChuLotHS
    """)
    List<ThiSinhDTO> findTSByKeyword(@Param("keyword") String keyword);

    @Query("""
        SELECT new com.example.lvtn.dto.ThiSinhDTO(ts.maHS, ts.soBaoDanh, ts.phongThi, ts.phongThiChuyen,
        hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh, hs.maTHCS, thcs.tenTHCS,
        hs.tenTHCSNgoaiTPCT, hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT, ts.maTHPT, thpt.tenTHPT, thpt.thptChuyen,
        ts.maLopChuyen, lc.tenLopChuyen, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        LEFT JOIN LopChuyen lc ON ts.maLopChuyen = lc.maLopChuyen
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        LEFT JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        WHERE ts.maTHPT = :maTHPT
        ORDER BY hs.tenHS, hs.hoVaChuLotHS
    """)
    List<ThiSinhDTO> findTSByMaTHPT(@Param("maTHPT") String maTHPT);

    @Query("""
        SELECT new com.example.lvtn.dto.ThiSinhDTO(ts.maHS, ts.soBaoDanh, ts.phongThi, ts.phongThiChuyen,
        hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh, hs.maTHCS, thcs.tenTHCS,
        hs.tenTHCSNgoaiTPCT, hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT, ts.maTHPT, thpt.tenTHPT, thpt.thptChuyen,
        ts.maLopChuyen, lc.tenLopChuyen, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        LEFT JOIN LopChuyen lc ON ts.maLopChuyen = lc.maLopChuyen
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        LEFT JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        WHERE ts.maTHPT = :maTHPT
        AND LOWER(CONCAT(hs.hoVaChuLotHS, ' ', hs.tenHS)) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY ts.maLopChuyen, hs.tenHS, hs.hoVaChuLotHS
    """)
    List<ThiSinhDTO> findTSByMaTHPTAndKeyword(@Param("maTHPT") String maTHPT, @Param("keyword") String keyword);

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaThiDTO(ts.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, thpt.thptChuyen)
        FROM ThiSinh ts
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        WHERE ts.soBaoDanh = :soBaoDanh
    """)
    KetQuaThiDTO findTSBySoBaoDanh(@Param("soBaoDanh") String soBaoDanh);

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaTuyenSinhDTO(hs.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
        hs.maTHCS, thcs.tenTHCS, ts.maTHPT, thpt.tenTHPT, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, nv.id.thuTu, thpt2.tenTHPT)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON thcs.maTHCS = hs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        LEFT JOIN NguyenVong nv ON nv.id.maHS = hs.maHS AND nv.ketQua = 'Đậu'
        LEFT JOIN THPT thpt2 ON nv.maTHPT = thpt2.maTHPT
        ORDER BY ts.soBaoDanh
    """)
    List<KetQuaTuyenSinhDTO> findKQTS();

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaTuyenSinhDTO(hs.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
        hs.maTHCS, thcs.tenTHCS, ts.maTHPT, thpt.tenTHPT, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, nv.id.thuTu, thpt2.tenTHPT)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON thcs.maTHCS = hs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        LEFT JOIN NguyenVong nv ON nv.id.maHS = hs.maHS AND nv.ketQua = 'Đậu'
        LEFT JOIN THPT thpt2 ON nv.maTHPT = thpt2.maTHPT
        WHERE ts.maTHPT = :maTHPT
        ORDER BY ts.soBaoDanh
    """)
    List<KetQuaTuyenSinhDTO> findKQTSByMaTHPT(@Param("maTHPT") String maTHPT);

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaTuyenSinhDTO(hs.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
        hs.maTHCS, thcs.tenTHCS, ts.maTHPT, thpt.tenTHPT, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, nv.id.thuTu, thpt2.tenTHPT)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON thcs.maTHCS = hs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        LEFT JOIN NguyenVong nv ON nv.id.maHS = hs.maHS AND nv.ketQua = 'Đậu'
        LEFT JOIN THPT thpt2 ON nv.maTHPT = thpt2.maTHPT
        WHERE ts.maTHPT = :maTHPT AND hs.maHS = :maHS
    """)
    KetQuaTuyenSinhDTO findKQTSByMaTHPTAndMaHS(@Param("maTHPT") String maTHPT, @Param("maHS") String maHS);

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaTuyenSinhDTO(hs.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
        hs.maTHCS, thcs.tenTHCS, ts.maTHPT, thpt.tenTHPT, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, nv.id.thuTu, thpt2.tenTHPT)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON thcs.maTHCS = hs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        LEFT JOIN NguyenVong nv ON nv.id.maHS = hs.maHS AND nv.ketQua = 'Đậu'
        LEFT JOIN THPT thpt2 ON nv.maTHPT = thpt2.maTHPT
        WHERE ts.maTHPT = :maTHPT AND ts.soBaoDanh = :soBaoDanh
    """)
    KetQuaTuyenSinhDTO findKQTSByMaTHPTAndSoBaoDanh(@Param("maTHPT") String maTHPT, @Param("soBaoDanh") String soBaoDanh);

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaTuyenSinhDTO(hs.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
        hs.maTHCS, thcs.tenTHCS, ts.maTHPT, thpt.tenTHPT, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, nv.id.thuTu, thpt2.tenTHPT)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON thcs.maTHCS = hs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        LEFT JOIN NguyenVong nv ON nv.id.maHS = hs.maHS AND nv.ketQua = 'Đậu'
        LEFT JOIN THPT thpt2 ON nv.maTHPT = thpt2.maTHPT
        WHERE ts.maTHPT = :maTHPT
            AND LOWER(CONCAT(hs.hoVaChuLotHS, ' ', hs.tenHS)) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<KetQuaTuyenSinhDTO> findKQTSByMaTHPTAndTenHS(@Param("maTHPT") String maTHPT, @Param("keyword") String keyword);

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaTuyenSinhDTO(hs.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
        hs.maTHCS, thcs.tenTHCS, ts.maTHPT, thpt.tenTHPT, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, nv.id.thuTu, thpt2.tenTHPT)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON thcs.maTHCS = hs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        LEFT JOIN NguyenVong nv ON nv.id.maHS = hs.maHS AND nv.ketQua = 'Đậu'
        LEFT JOIN THPT thpt2 ON nv.maTHPT = thpt2.maTHPT
        WHERE ts.soBaoDanh = :soBaoDanh
    """)
    KetQuaTuyenSinhDTO findKQTSBySoBaoDanh(@Param("soBaoDanh") String soBaoDanh);

    @Query("""
        SELECT new com.example.lvtn.dto.KetQuaTuyenSinhDTO(hs.maHS, ts.soBaoDanh, hs.hoVaChuLotHS, hs.tenHS,
        hs.maTHCS, thcs.tenTHCS, ts.maTHPT, thpt.tenTHPT, dtut.diemCong, dtkk.diemCong, dtkkc.diemCong,
        kqt.diemToan, kqt.diemVan, kqt.diemMonThu3, kqt.diemMonChuyen, nv.id.thuTu, thpt2.tenTHPT)
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THCS thcs ON thcs.maTHCS = hs.maTHCS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        JOIN KetQuaThi kqt ON ts.maHS = kqt.maHS
        LEFT JOIN NguyenVong nv ON nv.id.maHS = hs.maHS AND nv.ketQua = 'Đậu'
        LEFT JOIN THPT thpt2 ON nv.maTHPT = thpt2.maTHPT
        WHERE hs.maHS = :maHS
    """)
    KetQuaTuyenSinhDTO findKQTSByMaHS(@Param("maHS") String maHS);

    @Modifying
    @Transactional
    @Query("""
        UPDATE ThiSinh ts
        SET ts.soBaoDanh = NULL,
            ts.phongThi = NULL,
            ts.phongThiChuyen = NULL
    """)
    void resetThiSinhData();

    @Query("""
        SELECT ts.maHS, ts.maTHPT, ts.maLopChuyen, thpt.sttTHPT
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        ORDER BY ts.maTHPT, hs.tenHS, hs.hoVaChuLotHS
    """)
    List<Object[]> sortThiSinhChung();

    @Query("""
        SELECT ts.maHS, ts.maTHPT, ts.maLopChuyen, thpt.sttTHPT
        FROM ThiSinh ts
        JOIN HocSinh hs ON ts.maHS = hs.maHS
        JOIN THPT thpt ON ts.maTHPT = thpt.maTHPT
        WHERE thpt.thptChuyen = true AND ts.maLopChuyen IS NOT NULL
        ORDER BY ts.maTHPT, ts.maLopChuyen, hs.tenHS, hs.hoVaChuLotHS
    """)
    List<Object[]> sortThiSinhChuyen();
}
