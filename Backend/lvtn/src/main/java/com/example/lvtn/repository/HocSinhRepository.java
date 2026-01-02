package com.example.lvtn.repository;

import com.example.lvtn.dto.HocSinhFullData;
import com.example.lvtn.dto.TaiKhoanHSNgoaiTinh;
import com.example.lvtn.dto.TaiKhoanHSTHCS;
import com.example.lvtn.model.HocSinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HocSinhRepository extends JpaRepository<HocSinh,String> {

    // Hàm tìm kiếm học sinh theo mã học sinh
    @Query("""
        SELECT new com.example.lvtn.dto.HocSinhFullData(hs.maHS, hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh,
        hs.maDT, dt.tenDT, hs.diaChiThuongTru, hs.choOHienNay, hs.maTHCS, thcs.tenTHCS, px.tenPhuongXa, hs.namTotNghiepTHCS,
        hs.maDTUT, dtut.tenDTUT, dtut.diemCong, hs.maDTKK, dtkk.tenDTKK, dtkk.diemCong, hs.maDTKKChuyen, dtkkc.tenDTKKChuyen, dtkkc.diemCong,
        hs.ngoaiNguDangHoc, hs.ngoaiNguDuThi, hs.soDienThoai, hs.diemTBMonChuyen, hs.tongDiemTBLop9, hs.anhDaiDien, hs.tenTHCSNgoaiTPCT,
        hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT)
        FROM HocSinh hs
        LEFT JOIN DanToc dt ON hs.maDT = dt.maDT
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN PhuongXa px ON thcs.maPhuongXa = px.maPhuongXa
        LEFT JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        LEFT JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        LEFT JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        WHERE hs.maHS = :maHS
    """)
    Optional<HocSinhFullData> findHocSinhByMaHS(@Param("maHS") String maHS);

    // Hàm truy xuất danh sách thí sinh đang học tại trường THCS tương ứng
    @Query("""
        SELECT new com.example.lvtn.dto.HocSinhFullData(hs.maHS, hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh,
        hs.maDT, dt.tenDT, hs.diaChiThuongTru, hs.choOHienNay, hs.maTHCS, thcs.tenTHCS, px.tenPhuongXa, hs.namTotNghiepTHCS,
        hs.maDTUT, dtut.tenDTUT, dtut.diemCong, hs.maDTKK, dtkk.tenDTKK, dtkk.diemCong, hs.maDTKKChuyen, dtkkc.tenDTKKChuyen, dtkkc.diemCong,
        hs.ngoaiNguDangHoc, hs.ngoaiNguDuThi, hs.soDienThoai, hs.diemTBMonChuyen, hs.tongDiemTBLop9, hs.anhDaiDien, hs.tenTHCSNgoaiTPCT,
        hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT)
        FROM HocSinh hs
        LEFT JOIN DanToc dt ON hs.maDT = dt.maDT
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN PhuongXa px ON thcs.maPhuongXa = px.maPhuongXa
        LEFT JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        LEFT JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        LEFT JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        WHERE hs.maTHCS = :maTHCS
    """)
    List<HocSinhFullData> findHocSinhByMaTHCS(@Param("maTHCS") String maTHCS);

    // Hàm truy xuất danh sách tài khoản học sinh tại trường THCS
    @Query("""
        SELECT new com.example.lvtn.dto.TaiKhoanHSTHCS(tk.tenTK, tk.matKhau, hs.hoVaChuLotHS, hs.tenHS,
        hs.gioiTinh, hs.ngaySinh, hs.noiSinh)
        FROM HocSinh hs
        JOIN TaiKhoan tk ON hs.maHS = tk.tenTK
        WHERE hs.maTHCS = :maTHCS
    """)
    List<TaiKhoanHSTHCS> findTKHocSinhByMaTHCS(@Param("maTHCS") String maTHCS);

    @Query("""
        SELECT hs.maHS
        FROM HocSinh hs
        WHERE hs.maTHCS = :maTHCS
    """)
    List<String> findMaHSByMaTHCS(@Param("maTHCS") String maTHCS);

    boolean existsByMaHSAndMaTHCS(String maHS, String maTHCS);

    // Hàm truy xuất danh sách thí sinh đang học tại trường THCS tương ứng theo keyword
    @Query("""
        SELECT new com.example.lvtn.dto.HocSinhFullData(hs.maHS, hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh,
        hs.maDT, dt.tenDT, hs.diaChiThuongTru, hs.choOHienNay, hs.maTHCS, thcs.tenTHCS, px.tenPhuongXa, hs.namTotNghiepTHCS,
        hs.maDTUT, dtut.tenDTUT, dtut.diemCong, hs.maDTKK, dtkk.tenDTKK, dtkk.diemCong, hs.maDTKKChuyen, dtkkc.tenDTKKChuyen, dtkkc.diemCong,
        hs.ngoaiNguDangHoc, hs.ngoaiNguDuThi, hs.soDienThoai, hs.diemTBMonChuyen, hs.tongDiemTBLop9, hs.anhDaiDien, hs.tenTHCSNgoaiTPCT,
        hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT)
        FROM HocSinh hs
        LEFT JOIN DanToc dt ON hs.maDT = dt.maDT
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN PhuongXa px ON thcs.maPhuongXa = px.maPhuongXa
        LEFT JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        LEFT JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        LEFT JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        WHERE hs.maTHCS = :maTHCS
            AND (
                LOWER(hs.maHS) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(CONCAT(hs.hoVaChuLotHS, ' ', hs.tenHS)) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
    """)
    List<HocSinhFullData> findHocSinhByMaTHCSAndKeyword(@Param("maTHCS") String maTHCS, @Param("keyword") String keyword);

    // Hàm truy xuất danh sách thí sinh ngoại tỉnh đăng ký nguyện vọng cao nhất vào trường THPT (dùng trong cập nhật, xóa)
    @Query("""
        SELECT new com.example.lvtn.dto.HocSinhFullData(hs.maHS, hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh,
        hs.maDT, dt.tenDT, hs.diaChiThuongTru, hs.choOHienNay, hs.maTHCS, thcs.tenTHCS, px.tenPhuongXa, hs.namTotNghiepTHCS,
        hs.maDTUT, dtut.tenDTUT, dtut.diemCong, hs.maDTKK, dtkk.tenDTKK, dtkk.diemCong, hs.maDTKKChuyen, dtkkc.tenDTKKChuyen, dtkkc.diemCong,
        hs.ngoaiNguDangHoc, hs.ngoaiNguDuThi, hs.soDienThoai, hs.diemTBMonChuyen, hs.tongDiemTBLop9, hs.anhDaiDien, hs.tenTHCSNgoaiTPCT,
        hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT)
        FROM NguyenVong nv
        JOIN HocSinh hs ON nv.id.maHS = hs.maHS
        LEFT JOIN DanToc dt ON hs.maDT = dt.maDT
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN PhuongXa px ON thcs.maPhuongXa = px.maPhuongXa
        LEFT JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        LEFT JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        LEFT JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        WHERE nv.maTHPT = :maTHPT
            AND nv.id.thuTu = (
                SELECT MIN(nv2.id.thuTu)
                FROM NguyenVong nv2
                WHERE nv2.id.maHS = nv.id.maHS
            )
            AND hs.maTHCS = '0'
    """)
    List<HocSinhFullData> findHocSinhNgoaiTinhByMaTHPT(@Param("maTHPT") String maTHPT);

    // Hàm truy xuất danh sách tài khoản học sinh ngoại tỉnh thi tại trường THPT
    @Query("""
        SELECT new com.example.lvtn.dto.TaiKhoanHSNgoaiTinh(tk.tenTK, tk.matKhau, hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh,
        hs.ngaySinh, hs.noiSinh, hs.tenTHCSNgoaiTPCT, hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT)
        FROM NguyenVong nv
        JOIN HocSinh hs ON nv.id.maHS = hs.maHS
        JOIN TaiKhoan tk ON hs.maHS = tk.tenTK
        WHERE nv.maTHPT = :maTHPT
            AND nv.id.thuTu = (
                SELECT MIN(nv2.id.thuTu)
                FROM NguyenVong nv2
                WHERE nv2.id.maHS = nv.id.maHS
            )
            AND hs.maTHCS = '0'
    """)
    List<TaiKhoanHSNgoaiTinh> findTKHocSinhNgoaiTinhByMaTHPT(@Param("maTHPT") String maTHPT);

    @Query("""
        SELECT hs.maHS
        FROM NguyenVong nv
        JOIN HocSinh hs ON nv.id.maHS = hs.maHS
        WHERE nv.maTHPT = :maTHPT
            AND nv.id.thuTu = (
                SELECT MIN(nv2.id.thuTu)
                FROM NguyenVong nv2
                WHERE nv2.id.maHS = nv.id.maHS
            )
            AND hs.maTHCS = '0'
    """)
    List<String> findMaHSNgoaiTinhByMaTHPT(@Param("maTHPT") String maTHPT);

    @Query("""
        SELECT COUNT(hs) > 0
        FROM NguyenVong nv
        JOIN HocSinh hs ON nv.id.maHS = hs.maHS
        WHERE nv.maTHPT = :maTHPT
            AND nv.id.thuTu = (
                SELECT MIN(nv2.id.thuTu)
                FROM NguyenVong nv2
                WHERE nv2.id.maHS = nv.id.maHS
            )
            AND hs.maTHCS = '0'
            AND hs.maHS = maHS
    """)
    boolean existsHocSinhNgoaiTinhByMaHSAndMaTHPT(@Param("maHS") String maHS, @Param("maTHPT") String maTHPT);

    // Hàm truy xuất danh sách thí sinh đăng ký nguyện vọng cao nhất vào trường THPT theo keyword
    @Query("""
        SELECT new com.example.lvtn.dto.HocSinhFullData(hs.maHS, hs.hoVaChuLotHS, hs.tenHS, hs.gioiTinh, hs.ngaySinh, hs.noiSinh,
        hs.maDT, dt.tenDT, hs.diaChiThuongTru, hs.choOHienNay, hs.maTHCS, thcs.tenTHCS, px.tenPhuongXa, hs.namTotNghiepTHCS,
        hs.maDTUT, dtut.tenDTUT, dtut.diemCong, hs.maDTKK, dtkk.tenDTKK, dtkk.diemCong, hs.maDTKKChuyen, dtkkc.tenDTKKChuyen, dtkkc.diemCong,
        hs.ngoaiNguDangHoc, hs.ngoaiNguDuThi, hs.soDienThoai, hs.diemTBMonChuyen, hs.tongDiemTBLop9, hs.anhDaiDien, hs.tenTHCSNgoaiTPCT,
        hs.tenXaNgoaiTPCT, hs.tenTinhNgoaiTPCT)
        FROM NguyenVong nv
        JOIN HocSinh hs ON nv.id.maHS = hs.maHS
        LEFT JOIN DanToc dt ON hs.maDT = dt.maDT
        JOIN THCS thcs ON hs.maTHCS = thcs.maTHCS
        JOIN PhuongXa px ON thcs.maPhuongXa = px.maPhuongXa
        LEFT JOIN DTUT dtut ON hs.maDTUT = dtut.maDTUT
        LEFT JOIN DTKK dtkk ON hs.maDTKK = dtkk.maDTKK
        LEFT JOIN DTKKChuyen dtkkc ON hs.maDTKKChuyen = dtkkc.maDTKKChuyen
        WHERE nv.maTHPT = :maTHPT
            AND nv.id.thuTu = (
                SELECT MIN(nv2.id.thuTu)
                FROM NguyenVong nv2
                WHERE nv2.id.maHS = nv.id.maHS
            ) AND hs.maTHCS = '0'
            AND (
                LOWER(nv.id.maHS) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(CONCAT(hs.hoVaChuLotHS, ' ', hs.tenHS)) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
    """)
    List<HocSinhFullData> findHocSinhNgoaiTinhByMaTHPTAndKeyword(@Param("maTHPT") String maTHPT, @Param("keyword") String keyword);
}
