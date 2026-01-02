import 'package:json_annotation/json_annotation.dart';

part 'ketqua_thi.g.dart';

@JsonSerializable()
class KetQuaThi {
  final String maHS;
  final String soBaoDanh;
  final String hoVaChuLotHS;
  final String tenHS;
  final double? diemToan;
  final double? diemVan;
  final double? diemMonThu3;
  final double? diemMonChuyen;
  final bool thptChuyen;

  KetQuaThi({
    required this.maHS,
    required this.soBaoDanh,
    required this.hoVaChuLotHS,
    required this.tenHS,
    this.diemToan,
    this.diemVan,
    this.diemMonThu3,
    this.diemMonChuyen,
    required this.thptChuyen,
  });

  factory KetQuaThi.fromJson(Map<String, dynamic> json) =>
      _$KetQuaThiFromJson(json);

  Map<String, dynamic> toJson() => _$KetQuaThiToJson(this);
}
