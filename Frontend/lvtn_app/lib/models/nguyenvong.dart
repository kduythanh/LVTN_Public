import 'package:json_annotation/json_annotation.dart';

part 'nguyenvong.g.dart';

@JsonSerializable()
class NguyenVong {
  final String maHS;
  final int thuTu;
  final String maTHPT;
  final String tenTHPT;
  final String tenPhuongXaTHPT;
  final bool? nv2B;
  final bool? lopTiengPhap;
  final String? maLopChuyen;
  final String? tenLopChuyen;
  final String? monChuyen;
  final String? ketQua;

  NguyenVong({
    required this.maHS,
    required this.thuTu,
    required this.maTHPT,
    required this.tenTHPT,
    required this.tenPhuongXaTHPT,
    this.nv2B,
    this.lopTiengPhap,
    this.maLopChuyen,
    this.tenLopChuyen,
    this.monChuyen,
    this.ketQua,
  });

  factory NguyenVong.fromJson(Map<String, dynamic> json) =>
      _$NguyenVongFromJson(json);

  Map<String, dynamic> toJson() => _$NguyenVongToJson(this);
}
