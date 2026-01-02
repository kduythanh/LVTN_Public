import 'package:flutter/material.dart';
import 'package:lvtn_app/api/thcs_api.dart';
import 'package:lvtn_app/components/anh_hocsinh.dart';
import 'package:lvtn_app/models/hocsinh_fulldata.dart';
import 'package:lvtn_app/models/nguyenvong.dart';
import 'package:lvtn_app/theme/app_colors.dart';

class THCSHocSinhDetail extends StatefulWidget {
  final String maHS;
  const THCSHocSinhDetail({super.key, required this.maHS});

  @override
  State<THCSHocSinhDetail> createState() => _THCSHocSinhDetailState();
}

class _THCSHocSinhDetailState extends State<THCSHocSinhDetail> {
  bool isLoading = true;
  HocSinhFullData? hocSinh;

  @override
  void initState() {
    super.initState();
    fetchHocSinhDetail();
  }

  Future<void> fetchHocSinhDetail() async {
    try {
      final res = await THCSApi.getHocSinhDetail(maHS: widget.maHS);
      final data = HocSinhFullData.fromJson(res.data['data']);
      setState(() {
        hocSinh = data;
        isLoading = false;
      });
    } catch (e) {
      debugPrint("Lỗi khi lấy thông tin học sinh: $e");
      setState(() => isLoading = false);
    }
  }

  Widget _buildSectionTitle(String title) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(8.0),
      color: AppColors.success,
      child: Text(
        title,
        style: const TextStyle(
          color: Colors.white,
          fontWeight: FontWeight.bold,
          fontSize: 16,
        ),
      ),
    );
  }

  Widget _buildRow(String label, String? value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0, horizontal: 8.0),
      child: Text.rich(
        TextSpan(
          children: [
            TextSpan(
              text: "$label: ",
              style: const TextStyle(fontWeight: FontWeight.bold),
            ),
            TextSpan(text: value ?? "-"),
          ],
        ),
      ),
    );
  }

  Widget _buildRowNV(String label, Widget value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0, horizontal: 8.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            label,
            style: const TextStyle(
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
          const SizedBox(height: 2),
          value,
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return SafeArea(child: const Center(child: CircularProgressIndicator()));
    }
    if (hocSinh == null) {
      return SafeArea(
        child: Center(
          child: Text(
            "Không tồn tại thông tin học sinh!",
            style: TextStyle(fontSize: 18, color: AppColors.danger),
          ),
        ),
      );
    }
    final hs = hocSinh!;
    final List<NguyenVong?> nvArr = List.generate(5, (index) => null);
    for (final nv in hs.nguyenVong) {
      if (nv.thuTu >= 1 && nv.thuTu <= 5) {
        nvArr[nv.thuTu - 1] = nv;
      }
    }
    return SafeArea(
      child: RefreshIndicator(
        onRefresh: fetchHocSinhDetail,
        child: SingleChildScrollView(
          physics: const AlwaysScrollableScrollPhysics(),
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                _buildSectionTitle("Phần 1: Thông tin cá nhân học sinh"),
                const SizedBox(height: 8),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      width: 90,
                      height: 120, // tương ứng 3x4
                      decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey),
                        borderRadius: BorderRadius.circular(4),
                      ),
                      clipBehavior: Clip.hardEdge,
                      child: AnhHocSinh(maHS: widget.maHS),
                    ),
                  ],
                ),
                _buildRow("Mã học sinh", hs.thongTinHS.maHS),
                _buildRow(
                  "Họ và tên",
                  "${hs.thongTinHS.hoVaChuLotHS} ${hs.thongTinHS.tenHS}",
                ),
                _buildRow(
                  "Giới tính",
                  hs.thongTinHS.gioiTinh == true ? "Nữ" : "Nam",
                ),
                _buildRow("Ngày sinh", hs.thongTinHS.ngaySinh),
                _buildRow("Nơi sinh", hs.thongTinHS.noiSinh),
                _buildRow("Dân tộc", hs.thongTinHS.tenDT),
                _buildRow("Địa chỉ thường trú", hs.thongTinHS.diaChiThuongTru),
                _buildRow("Chỗ ở hiện nay", hs.thongTinHS.choOHienNay),
                _buildRow(
                  "Học sinh trường THCS",
                  "${hs.thongTinHS.tenTHCS} (${hs.thongTinHS.tenPhuongXaTHCS})",
                ),

                const SizedBox(height: 8),
                _buildSectionTitle("Phần 2: Kết quả học tập cấp THCS"),
                for (var kq in hs.kqHocTap)
                  _buildRow(
                    "Lớp ${kq.lop}",
                    "Học tập: ${kq.hocTap}, Rèn luyện: ${kq.renLuyen}",
                  ),
                _buildRow(
                  "Tổng điểm TB lớp 9",
                  hs.thongTinHS.tongDiemTBLop9.toString(),
                ),
                _buildRow(
                  "Năm tốt nghiệp THCS",
                  hs.thongTinHS.namTotNghiepTHCS.toString(),
                ),

                const SizedBox(height: 8),
                _buildSectionTitle("Phần 3: Thông tin đăng ký dự thi"),
                _buildRow(
                  "Đối tượng ưu tiên",
                  "${hs.thongTinHS.tenDTUT} (Điểm cộng: ${hs.thongTinHS.diemCongDTUT})",
                ),
                _buildRow(
                  "Đối tượng khuyến khích",
                  "${hs.thongTinHS.tenDTKK} (Điểm cộng: ${hs.thongTinHS.diemCongDTKK})",
                ),
                _buildRow(
                  "Đối tượng KK (chuyên)",
                  "${hs.thongTinHS.tenDTKKChuyen} (Điểm cộng: ${hs.thongTinHS.diemCongDTKKChuyen})",
                ),
                for (int i = 0; i < nvArr.length; i++)
                  _buildRowNV(
                    "Nguyện vọng ${i + 1}",
                    nvArr[i] != null
                        ? Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                nvArr[i]!.thuTu == 1 &&
                                        nvArr[i]!.tenLopChuyen != null
                                    ? "${nvArr[i]!.tenTHPT} (${nvArr[i]!.tenPhuongXaTHPT}) - ${nvArr[i]!.tenLopChuyen}"
                                    : "${nvArr[i]!.tenTHPT} (${nvArr[i]!.tenPhuongXaTHPT})",
                              ),
                              if (nvArr[i]!.thuTu == 2)
                                Padding(
                                  padding: const EdgeInsets.only(top: 2.0),
                                  child: Row(
                                    children: [
                                      if (nvArr[i]!.nv2B == true)
                                        const Text(
                                          "(Nguyện vọng 2B)",
                                          style: TextStyle(
                                            fontStyle: FontStyle.italic,
                                          ),
                                        ),
                                      if (nvArr[i]!.lopTiengPhap == true) ...[
                                        if (nvArr[i]!.nv2B == true)
                                          const SizedBox(
                                            width: 4,
                                          ), // khoảng cách giữa 2 ghi chú
                                        const Text(
                                          "(Lớp tiếng Pháp)",
                                          style: TextStyle(
                                            fontStyle: FontStyle.italic,
                                          ),
                                        ),
                                      ],
                                    ],
                                  ),
                                ),
                              if (nvArr[i]!.thuTu == 1)
                                Padding(
                                  padding: const EdgeInsets.only(top: 2.0),
                                  child: Text(
                                    "Điểm trung bình môn chuyên lớp 9: ${hs.thongTinHS.diemTBMonChuyen}",
                                  ),
                                ),
                            ],
                          )
                        : const Text("-"),
                  ),
                _buildRow("Ngoại ngữ đang học", hs.thongTinHS.ngoaiNguDangHoc),
                _buildRow("Ngoại ngữ dự thi", hs.thongTinHS.ngoaiNguDuThi),
                _buildRow("Số điện thoại", hs.thongTinHS.soDienThoai),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
