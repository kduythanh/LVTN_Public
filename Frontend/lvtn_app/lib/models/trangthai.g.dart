// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'trangthai.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

TrangThai _$TrangThaiFromJson(Map<String, dynamic> json) => TrangThai(
  maTT: (json['maTT'] as num).toInt(),
  tenTrangThai: json['tenTrangThai'] as String,
  kieuDuLieu: json['kieuDuLieu'] as String,
  giaTriBoolean: json['giaTriBoolean'] as bool?,
  giaTriTimestamp: _$JsonConverterFromJson<List<dynamic>, DateTime>(
    json['giaTriTimestamp'],
    const TimestampConverter().fromJson,
  ),
  giaTriChuoi: json['giaTriChuoi'] as String?,
  moTa: json['moTa'] as String?,
  tgCapNhat: _$JsonConverterFromJson<List<dynamic>, DateTime>(
    json['tgCapNhat'],
    const TimestampConverter().fromJson,
  ),
);

Map<String, dynamic> _$TrangThaiToJson(TrangThai instance) => <String, dynamic>{
  'maTT': instance.maTT,
  'tenTrangThai': instance.tenTrangThai,
  'kieuDuLieu': instance.kieuDuLieu,
  'giaTriBoolean': instance.giaTriBoolean,
  'giaTriTimestamp': _$JsonConverterToJson<List<dynamic>, DateTime>(
    instance.giaTriTimestamp,
    const TimestampConverter().toJson,
  ),
  'giaTriChuoi': instance.giaTriChuoi,
  'moTa': instance.moTa,
  'tgCapNhat': _$JsonConverterToJson<List<dynamic>, DateTime>(
    instance.tgCapNhat,
    const TimestampConverter().toJson,
  ),
};

Value? _$JsonConverterFromJson<Json, Value>(
  Object? json,
  Value? Function(Json json) fromJson,
) => json == null ? null : fromJson(json as Json);

Json? _$JsonConverterToJson<Json, Value>(
  Value? value,
  Json? Function(Value value) toJson,
) => value == null ? null : toJson(value);
