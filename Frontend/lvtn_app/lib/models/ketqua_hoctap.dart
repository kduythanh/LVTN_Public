import 'package:json_annotation/json_annotation.dart';

part 'ketqua_hoctap.g.dart';

@JsonSerializable()
class KetQuaHocTap {
  final String maHS;
  final int lop;
  final String hocTap;
  final String renLuyen;

  KetQuaHocTap({
    required this.maHS,
    required this.lop,
    required this.hocTap,
    required this.renLuyen,
  });

  factory KetQuaHocTap.fromJson(Map<String, dynamic> json) =>
      _$KetQuaHocTapFromJson(json);

  Map<String, dynamic> toJson() => _$KetQuaHocTapToJson(this);
}
