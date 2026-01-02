import 'package:json_annotation/json_annotation.dart';

part 'chitieu.g.dart';

@JsonSerializable()
class ChiTieu {
  final String maTHPT;
  final String tenTHPT;
  final String diaChi;
  final int chiTieu;
  final int soLuongDangKy;

  ChiTieu({
    required this.maTHPT,
    required this.tenTHPT,
    required this.diaChi,
    required this.chiTieu,
    required this.soLuongDangKy,
  });

  factory ChiTieu.fromJson(Map<String, dynamic> json) =>
      _$ChiTieuFromJson(json);

  Map<String, dynamic> toJson() => _$ChiTieuToJson(this);
}
