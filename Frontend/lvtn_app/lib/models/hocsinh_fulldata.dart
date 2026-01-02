import 'package:json_annotation/json_annotation.dart';
import 'package:lvtn_app/models/ketqua_hoctap.dart';
import 'package:lvtn_app/models/nguyenvong.dart';
import 'package:lvtn_app/models/thongtin_hocsinh.dart';

part 'hocsinh_fulldata.g.dart';

@JsonSerializable(explicitToJson: true)
class HocSinhFullData {
  final ThongTinHocSinh thongTinHS;
  final List<KetQuaHocTap> kqHocTap;
  final List<NguyenVong> nguyenVong;

  HocSinhFullData({
    required this.thongTinHS,
    required this.kqHocTap,
    required this.nguyenVong,
  });

  factory HocSinhFullData.fromJson(Map<String, dynamic> json) =>
      _$HocSinhFullDataFromJson(json);

  Map<String, dynamic> toJson() => _$HocSinhFullDataToJson(this);
}
