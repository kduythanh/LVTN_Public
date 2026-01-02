import 'package:dio/dio.dart';
import 'package:lvtn_app/api/api_client.dart';

class SGDDTApi {
  static Future<Response> getListThiSinh() async {
    try {
      final response = await ApiClient.dio.get('/sgddt/thisinh');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getKQThiBySBD({required String sbd}) async {
    try {
      final response = await ApiClient.dio.get('/sgddt/thisinh/$sbd');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> updateKQThiBySBD({
    required String sbd,
    required String maHS,
    double? diemToan,
    double? diemVan,
    double? diemMonThu3,
    double? diemMonChuyen,
  }) async {
    try {
      FormData formData = FormData.fromMap({
        'maHS': maHS,
        'diemToan': diemToan,
        'diemVan': diemVan,
        'diemMonThu3': diemMonThu3,
        'diemMonChuyen': diemMonChuyen,
      });
      final response = await ApiClient.dio.patch(
        '/sgddt/thisinh/kqthi/$sbd',
        data: formData,
        options: Options(contentType: 'multipart/form-data'),
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getTrangThai() async {
    try {
      final response = await ApiClient.dio.get('/sgddt/trangthai');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> updateTrangThai({
    required int maTT,
    required String kieuDuLieu,
    required String newVal,
  }) async {
    try {
      final response = await ApiClient.dio.patch(
        '/sgddt/trangthai/$maTT',
        queryParameters: {'kieuDuLieu': kieuDuLieu, 'newVal': newVal},
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }
}
