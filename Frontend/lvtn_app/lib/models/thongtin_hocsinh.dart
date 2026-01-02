import 'package:json_annotation/json_annotation.dart';

part 'thongtin_hocsinh.g.dart';

@JsonSerializable()
class ThongTinHocSinh {
  final String maHS;
  final String hoVaChuLotHS;
  final String tenHS;
  final bool? gioiTinh;
  final String? ngaySinh;
  final String? noiSinh;
  final int? maDT;
  final String? tenDT;
  final String? diaChiThuongTru;
  final String? choOHienNay;
  final String maTHCS;
  final String tenTHCS;
  final String tenPhuongXaTHCS;
  final int? namTotNghiepTHCS;
  final int? maDTUT;
  final String? tenDTUT;
  final double? diemCongDTUT;
  final int? maDTKK;
  final String? tenDTKK;
  final double? diemCongDTKK;
  final int? maDTKKChuyen;
  final String? tenDTKKChuyen;
  final double? diemCongDTKKChuyen;
  final String? ngoaiNguDangHoc;
  final String? ngoaiNguDuThi;
  final String? soDienThoai;
  final double? diemTBMonChuyen;
  final double? tongDiemTBLop9;
  final String? anhDaiDien;
  final String? tenTHCSNgoaiTPCT;
  final String? tenXaNgoaiTPCT;
  final String? tenTinhNgoaiTPCT;

  ThongTinHocSinh({
    required this.maHS,
    required this.hoVaChuLotHS,
    required this.tenHS,
    this.gioiTinh,
    this.ngaySinh,
    this.noiSinh,
    this.maDT,
    this.tenDT,
    this.diaChiThuongTru,
    this.choOHienNay,
    required this.maTHCS,
    required this.tenTHCS,
    required this.tenPhuongXaTHCS,
    this.namTotNghiepTHCS,
    this.maDTUT,
    this.tenDTUT,
    this.diemCongDTUT,
    this.maDTKK,
    this.tenDTKK,
    this.diemCongDTKK,
    this.maDTKKChuyen,
    this.tenDTKKChuyen,
    this.diemCongDTKKChuyen,
    this.ngoaiNguDangHoc,
    this.ngoaiNguDuThi,
    this.soDienThoai,
    this.diemTBMonChuyen,
    this.tongDiemTBLop9,
    this.anhDaiDien,
    this.tenTHCSNgoaiTPCT,
    this.tenXaNgoaiTPCT,
    this.tenTinhNgoaiTPCT,
  });

  factory ThongTinHocSinh.fromJson(Map<String, dynamic> json) =>
      _$ThongTinHocSinhFromJson(json);

  Map<String, dynamic> toJson() => _$ThongTinHocSinhToJson(this);
}
