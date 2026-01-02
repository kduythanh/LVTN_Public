import 'package:dio/dio.dart';
import 'package:lvtn_app/api/api_client.dart';

class THPTApi {
  static Future<Response> getListHocSinh({required String maTHPT}) async {
    try {
      final response = await ApiClient.dio.get('/thpt/$maTHPT/hocsinh');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> searchHocSinh({
    required String maTHPT,
    required String keyword,
  }) async {
    try {
      final response = await ApiClient.dio.get(
        '/thpt/$maTHPT/hocsinh/search',
        queryParameters: {'keyword': keyword},
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getHocSinhDetail({required String maHS}) async {
    try {
      final response = await ApiClient.dio.get('/thpt/hocsinh/$maHS');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getListThiSinh({required String maTHPT}) async {
    try {
      final response = await ApiClient.dio.get('/thpt/$maTHPT/thisinh');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> searchThiSinh({
    required String maTHPT,
    required String keyword,
  }) async {
    try {
      final response = await ApiClient.dio.get(
        '/thpt/$maTHPT/thisinh/search',
        queryParameters: {'keyword': keyword},
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }
}
