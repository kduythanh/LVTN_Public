import 'package:json_annotation/json_annotation.dart';

part 'taikhoan.g.dart';

@JsonSerializable()
class TaiKhoan {
  final String tenTK;
  final int maLoaiTK;
  final String soDinhDanh;
  final String tenDinhDanh;

  TaiKhoan({
    required this.tenTK,
    required this.maLoaiTK,
    required this.soDinhDanh,
    required this.tenDinhDanh,
  });

  factory TaiKhoan.fromJson(Map<String, dynamic> json) =>
      _$TaiKhoanFromJson(json);

  Map<String, dynamic> toJson() => _$TaiKhoanToJson(this);
}
