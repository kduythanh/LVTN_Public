// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'hocsinh_fulldata.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

HocSinhFullData _$HocSinhFullDataFromJson(Map<String, dynamic> json) =>
    HocSinhFullData(
      thongTinHS: ThongTinHocSinh.fromJson(
        json['thongTinHS'] as Map<String, dynamic>,
      ),
      kqHocTap: (json['kqHocTap'] as List<dynamic>)
          .map((e) => KetQuaHocTap.fromJson(e as Map<String, dynamic>))
          .toList(),
      nguyenVong: (json['nguyenVong'] as List<dynamic>)
          .map((e) => NguyenVong.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$HocSinhFullDataToJson(HocSinhFullData instance) =>
    <String, dynamic>{
      'thongTinHS': instance.thongTinHS.toJson(),
      'kqHocTap': instance.kqHocTap.map((e) => e.toJson()).toList(),
      'nguyenVong': instance.nguyenVong.map((e) => e.toJson()).toList(),
    };
