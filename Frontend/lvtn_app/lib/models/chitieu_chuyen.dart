import 'package:json_annotation/json_annotation.dart';

part 'chitieu_chuyen.g.dart';

@JsonSerializable()
class ChiTieuChuyen {
  final String maTHPT;
  final String tenTHPT;
  final String maLopChuyen;
  final String tenLopChuyen;
  final int chiTieu;
  final int soLuongDangKy;

  ChiTieuChuyen({
    required this.maTHPT,
    required this.tenTHPT,
    required this.maLopChuyen,
    required this.tenLopChuyen,
    required this.chiTieu,
    required this.soLuongDangKy,
  });

  factory ChiTieuChuyen.fromJson(Map<String, dynamic> json) =>
      _$ChiTieuChuyenFromJson(json);

  Map<String, dynamic> toJson() => _$ChiTieuChuyenToJson(this);
}
