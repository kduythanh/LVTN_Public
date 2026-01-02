// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'taikhoan.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

TaiKhoan _$TaiKhoanFromJson(Map<String, dynamic> json) => TaiKhoan(
  tenTK: json['tenTK'] as String,
  maLoaiTK: (json['maLoaiTK'] as num).toInt(),
  soDinhDanh: json['soDinhDanh'] as String,
  tenDinhDanh: json['tenDinhDanh'] as String,
);

Map<String, dynamic> _$TaiKhoanToJson(TaiKhoan instance) => <String, dynamic>{
  'tenTK': instance.tenTK,
  'maLoaiTK': instance.maLoaiTK,
  'soDinhDanh': instance.soDinhDanh,
  'tenDinhDanh': instance.tenDinhDanh,
};
