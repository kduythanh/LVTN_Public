// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'ketqua_thi.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

KetQuaThi _$KetQuaThiFromJson(Map<String, dynamic> json) => KetQuaThi(
  maHS: json['maHS'] as String,
  soBaoDanh: json['soBaoDanh'] as String,
  hoVaChuLotHS: json['hoVaChuLotHS'] as String,
  tenHS: json['tenHS'] as String,
  diemToan: (json['diemToan'] as num?)?.toDouble(),
  diemVan: (json['diemVan'] as num?)?.toDouble(),
  diemMonThu3: (json['diemMonThu3'] as num?)?.toDouble(),
  diemMonChuyen: (json['diemMonChuyen'] as num?)?.toDouble(),
  thptChuyen: json['thptChuyen'] as bool,
);

Map<String, dynamic> _$KetQuaThiToJson(KetQuaThi instance) => <String, dynamic>{
  'maHS': instance.maHS,
  'soBaoDanh': instance.soBaoDanh,
  'hoVaChuLotHS': instance.hoVaChuLotHS,
  'tenHS': instance.tenHS,
  'diemToan': instance.diemToan,
  'diemVan': instance.diemVan,
  'diemMonThu3': instance.diemMonThu3,
  'diemMonChuyen': instance.diemMonChuyen,
  'thptChuyen': instance.thptChuyen,
};
