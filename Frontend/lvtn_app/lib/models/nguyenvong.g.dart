// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'nguyenvong.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

NguyenVong _$NguyenVongFromJson(Map<String, dynamic> json) => NguyenVong(
  maHS: json['maHS'] as String,
  thuTu: (json['thuTu'] as num).toInt(),
  maTHPT: json['maTHPT'] as String,
  tenTHPT: json['tenTHPT'] as String,
  tenPhuongXaTHPT: json['tenPhuongXaTHPT'] as String,
  nv2B: json['nv2B'] as bool?,
  lopTiengPhap: json['lopTiengPhap'] as bool?,
  maLopChuyen: json['maLopChuyen'] as String?,
  tenLopChuyen: json['tenLopChuyen'] as String?,
  monChuyen: json['monChuyen'] as String?,
  ketQua: json['ketQua'] as String?,
);

Map<String, dynamic> _$NguyenVongToJson(NguyenVong instance) =>
    <String, dynamic>{
      'maHS': instance.maHS,
      'thuTu': instance.thuTu,
      'maTHPT': instance.maTHPT,
      'tenTHPT': instance.tenTHPT,
      'tenPhuongXaTHPT': instance.tenPhuongXaTHPT,
      'nv2B': instance.nv2B,
      'lopTiengPhap': instance.lopTiengPhap,
      'maLopChuyen': instance.maLopChuyen,
      'tenLopChuyen': instance.tenLopChuyen,
      'monChuyen': instance.monChuyen,
      'ketQua': instance.ketQua,
    };
