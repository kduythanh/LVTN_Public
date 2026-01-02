import 'package:json_annotation/json_annotation.dart';
import 'package:lvtn_app/models/timestamp_converter.dart';

part 'trangthai.g.dart';

@JsonSerializable()
class TrangThai {
  final int maTT;
  final String tenTrangThai;
  final String kieuDuLieu;
  final bool? giaTriBoolean;
  @TimestampConverter()
  final DateTime? giaTriTimestamp;
  final String? giaTriChuoi;
  final String? moTa;
  @TimestampConverter()
  final DateTime? tgCapNhat;

  TrangThai({
    required this.maTT,
    required this.tenTrangThai,
    required this.kieuDuLieu,
    this.giaTriBoolean,
    this.giaTriTimestamp,
    this.giaTriChuoi,
    this.moTa,
    this.tgCapNhat,
  });

  factory TrangThai.fromJson(Map<String, dynamic> json) =>
      _$TrangThaiFromJson(json);

  Map<String, dynamic> toJson() => _$TrangThaiToJson(this);
}
