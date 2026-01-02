// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'thisinh_result.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ThiSinhResult _$ThiSinhResultFromJson(Map<String, dynamic> json) =>
    ThiSinhResult(
      maHS: json['maHS'] as String,
      soBaoDanh: json['soBaoDanh'] as String,
      hoVaChuLotHS: json['hoVaChuLotHS'] as String,
      tenHS: json['tenHS'] as String,
      maTHCS: json['maTHCS'] as String,
      tenTHCS: json['tenTHCS'] as String,
      maTHPT: json['maTHPT'] as String,
      tenTHPT: json['tenTHPT'] as String,
      diemCongUuTien: (json['diemCongUuTien'] as num?)?.toDouble(),
      diemCongKhuyenKhich: (json['diemCongKhuyenKhich'] as num?)?.toDouble(),
      diemCongKhuyenKhichChuyen: (json['diemCongKhuyenKhichChuyen'] as num?)
          ?.toDouble(),
      diemToan: (json['diemToan'] as num?)?.toDouble(),
      diemVan: (json['diemVan'] as num?)?.toDouble(),
      diemMonThu3: (json['diemMonThu3'] as num?)?.toDouble(),
      diemMonChuyen: (json['diemMonChuyen'] as num?)?.toDouble(),
      nguyenVongDau: (json['nguyenVongDau'] as num?)?.toInt(),
      truongTHPTDau: json['truongTHPTDau'] as String?,
    );

Map<String, dynamic> _$ThiSinhResultToJson(ThiSinhResult instance) =>
    <String, dynamic>{
      'maHS': instance.maHS,
      'soBaoDanh': instance.soBaoDanh,
      'hoVaChuLotHS': instance.hoVaChuLotHS,
      'tenHS': instance.tenHS,
      'maTHCS': instance.maTHCS,
      'tenTHCS': instance.tenTHCS,
      'maTHPT': instance.maTHPT,
      'tenTHPT': instance.tenTHPT,
      'diemCongUuTien': instance.diemCongUuTien,
      'diemCongKhuyenKhich': instance.diemCongKhuyenKhich,
      'diemCongKhuyenKhichChuyen': instance.diemCongKhuyenKhichChuyen,
      'diemToan': instance.diemToan,
      'diemVan': instance.diemVan,
      'diemMonThu3': instance.diemMonThu3,
      'diemMonChuyen': instance.diemMonChuyen,
      'nguyenVongDau': instance.nguyenVongDau,
      'truongTHPTDau': instance.truongTHPTDau,
    };
