// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'chitieu_chuyen.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChiTieuChuyen _$ChiTieuChuyenFromJson(Map<String, dynamic> json) =>
    ChiTieuChuyen(
      maTHPT: json['maTHPT'] as String,
      tenTHPT: json['tenTHPT'] as String,
      maLopChuyen: json['maLopChuyen'] as String,
      tenLopChuyen: json['tenLopChuyen'] as String,
      chiTieu: (json['chiTieu'] as num).toInt(),
      soLuongDangKy: (json['soLuongDangKy'] as num).toInt(),
    );

Map<String, dynamic> _$ChiTieuChuyenToJson(ChiTieuChuyen instance) =>
    <String, dynamic>{
      'maTHPT': instance.maTHPT,
      'tenTHPT': instance.tenTHPT,
      'maLopChuyen': instance.maLopChuyen,
      'tenLopChuyen': instance.tenLopChuyen,
      'chiTieu': instance.chiTieu,
      'soLuongDangKy': instance.soLuongDangKy,
    };
