// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'chitieu.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChiTieu _$ChiTieuFromJson(Map<String, dynamic> json) => ChiTieu(
  maTHPT: json['maTHPT'] as String,
  tenTHPT: json['tenTHPT'] as String,
  diaChi: json['diaChi'] as String,
  chiTieu: (json['chiTieu'] as num).toInt(),
  soLuongDangKy: (json['soLuongDangKy'] as num).toInt(),
);

Map<String, dynamic> _$ChiTieuToJson(ChiTieu instance) => <String, dynamic>{
  'maTHPT': instance.maTHPT,
  'tenTHPT': instance.tenTHPT,
  'diaChi': instance.diaChi,
  'chiTieu': instance.chiTieu,
  'soLuongDangKy': instance.soLuongDangKy,
};
