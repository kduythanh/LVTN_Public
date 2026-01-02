import 'package:json_annotation/json_annotation.dart';

part 'thisinh_result.g.dart';

@JsonSerializable()
class ThiSinhResult {
  final String maHS;
  final String soBaoDanh;
  final String hoVaChuLotHS;
  final String tenHS;
  final String maTHCS;
  final String tenTHCS;
  final String maTHPT;
  final String tenTHPT;
  final double? diemCongUuTien;
  final double? diemCongKhuyenKhich;
  final double? diemCongKhuyenKhichChuyen;
  final double? diemToan;
  final double? diemVan;
  final double? diemMonThu3;
  final double? diemMonChuyen;
  final int? nguyenVongDau;
  final String? truongTHPTDau;

  ThiSinhResult({
    required this.maHS,
    required this.soBaoDanh,
    required this.hoVaChuLotHS,
    required this.tenHS,
    required this.maTHCS,
    required this.tenTHCS,
    required this.maTHPT,
    required this.tenTHPT,
    this.diemCongUuTien,
    this.diemCongKhuyenKhich,
    this.diemCongKhuyenKhichChuyen,
    this.diemToan,
    this.diemVan,
    this.diemMonThu3,
    this.diemMonChuyen,
    this.nguyenVongDau,
    this.truongTHPTDau,
  });

  factory ThiSinhResult.fromJson(Map<String, dynamic> json) =>
      _$ThiSinhResultFromJson(json);

  Map<String, dynamic> toJson() => _$ThiSinhResultToJson(this);
}
