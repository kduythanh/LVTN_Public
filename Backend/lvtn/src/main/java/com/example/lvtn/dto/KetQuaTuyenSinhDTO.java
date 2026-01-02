package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaTuyenSinhDTO {

    private String maHS;

    private String soBaoDanh;

    private String hoVaChuLotHS;

    private String tenHS;

    private String maTHCS;

    private String tenTHCS;

    private String maTHPT;

    private String tenTHPT;

    private BigDecimal diemCongUuTien;

    private BigDecimal diemCongKhuyenKhich;

    private BigDecimal diemCongKhuyenKhichChuyen;

    private BigDecimal diemToan;

    private BigDecimal diemVan;

    private BigDecimal diemMonThu3;

    private BigDecimal diemMonChuyen;

    private Integer nguyenVongDau;

    private String truongTHPTDau;

    /**
     * Tính tổng điểm chung: Toán + Văn + Môn thứ 3 + Ưu tiên + Khuyến khích
     */
    public BigDecimal getTongDiemChung() {
        if (this.diemToan == null || this.diemVan == null || this.diemMonThu3 == null) {
            // Trả về 0.00 nếu thiếu bất kỳ môn chung nào
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        // Khởi tạo điểm ban đầu là 0
        BigDecimal tongDiem = BigDecimal.ZERO;

        // Sử dụng .add() để cộng các điểm lại. Kiểm tra null để đảm bảo an toàn
        tongDiem = tongDiem.add(this.diemToan);
        tongDiem = tongDiem.add(this.diemVan);
        tongDiem = tongDiem.add(this.diemMonThu3);
        tongDiem = tongDiem.add(this.diemCongUuTien != null ? this.diemCongUuTien : BigDecimal.ZERO);
        tongDiem = tongDiem.add(this.diemCongKhuyenKhich != null ? this.diemCongKhuyenKhich : BigDecimal.ZERO);

        // Giữ lại 2 chữ số thập phân, làm tròn Half Up (làm tròn lên nếu >= 0.005)
        return tongDiem.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Tính tổng điểm chuyên: Toán + Văn + Môn thứ 3 + 2*Chuyên + Khuyến khích Chuyên
     */
    public BigDecimal getTongDiemChuyen() {
        if (this.diemToan == null || this.diemVan == null || this.diemMonThu3 == null || this.diemMonChuyen == null) {
            // Trả về 0.00 nếu thiếu bất kỳ môn nào
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal tongDiem = BigDecimal.ZERO;
        BigDecimal DIEM_CHUYEN_HE_SO = new BigDecimal("2");

        // Điểm chung (như trên)
        tongDiem = tongDiem.add(this.diemToan);
        tongDiem = tongDiem.add(this.diemVan);
        tongDiem = tongDiem.add(this.diemMonThu3);

        // Điểm chuyên (nhân hệ số 2)
        BigDecimal diemChuyenDaNhanHeSo = BigDecimal.ZERO;
        // Sử dụng .multiply() để nhân
        diemChuyenDaNhanHeSo = this.diemMonChuyen.multiply(DIEM_CHUYEN_HE_SO);
        tongDiem = tongDiem.add(diemChuyenDaNhanHeSo);
        // Điểm khuyến khích chuyên
        tongDiem = tongDiem.add(this.diemCongKhuyenKhichChuyen != null ? this.diemCongKhuyenKhichChuyen : BigDecimal.ZERO);

        // Giữ lại 2 chữ số thập phân
        return tongDiem.setScale(2, RoundingMode.HALF_UP);
    }
}
