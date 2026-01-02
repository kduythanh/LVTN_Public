import 'package:json_annotation/json_annotation.dart';

part 'thisinh_fulldata.g.dart';

@JsonSerializable()
class ThiSinhFullData {
  final String maHS;
  final String? soBaoDanh;
  final String? phongThi;
  final String? phongThiChuyen;
  final String hoVaChuLotHS;
  final String tenHS;
  final bool? gioiTinh;
  final String? ngaySinh;
  final String? noiSinh;
  final String maTHCS;
  final String tenTHCS;
  final String? tenTHCSNgoaiTPCT;
  final String? tenXaNgoaiTPCT;
  final String? tenTinhNgoaiTPCT;
  final String maTHPT;
  final String tenTHPT;
  final bool thptChuyen;
  final String? maLopChuyen;
  final String? tenLopChuyen;
  final double? diemCongUuTien;
  final double? diemCongKhuyenKhich;
  final double? diemCongKhuyenKhichChuyen;
  final double? diemToan;
  final double? diemVan;
  final double? diemMonThu3;
  final double? diemMonChuyen;

  ThiSinhFullData({
    required this.maHS,
    this.soBaoDanh,
    this.phongThi,
    this.phongThiChuyen,
    required this.hoVaChuLotHS,
    required this.tenHS,
    this.gioiTinh,
    this.ngaySinh,
    this.noiSinh,
    required this.maTHCS,
    required this.tenTHCS,
    this.tenTHCSNgoaiTPCT,
    this.tenXaNgoaiTPCT,
    this.tenTinhNgoaiTPCT,
    required this.maTHPT,
    required this.tenTHPT,
    required this.thptChuyen,
    this.maLopChuyen,
    this.tenLopChuyen,
    this.diemCongUuTien,
    this.diemCongKhuyenKhich,
    this.diemCongKhuyenKhichChuyen,
    this.diemToan,
    this.diemVan,
    this.diemMonThu3,
    this.diemMonChuyen,
  });

  factory ThiSinhFullData.fromJson(Map<String, dynamic> json) =>
      _$ThiSinhFullDataFromJson(json);

  Map<String, dynamic> toJson() => _$ThiSinhFullDataToJson(this);
}
