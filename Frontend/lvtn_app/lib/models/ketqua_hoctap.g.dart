// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'ketqua_hoctap.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

KetQuaHocTap _$KetQuaHocTapFromJson(Map<String, dynamic> json) => KetQuaHocTap(
  maHS: json['maHS'] as String,
  lop: (json['lop'] as num).toInt(),
  hocTap: json['hocTap'] as String,
  renLuyen: json['renLuyen'] as String,
);

Map<String, dynamic> _$KetQuaHocTapToJson(KetQuaHocTap instance) =>
    <String, dynamic>{
      'maHS': instance.maHS,
      'lop': instance.lop,
      'hocTap': instance.hocTap,
      'renLuyen': instance.renLuyen,
    };
