import 'package:dio/dio.dart';
import 'package:lvtn_app/api/api_client.dart';

class THCSApi {
  static Future<Response> getListHocSinh({required String maTHCS}) async {
    try {
      final response = await ApiClient.dio.get('/thcs/$maTHCS/hocsinh');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> searchHocSinh({
    required String maTHCS,
    required String keyword,
  }) async {
    try {
      final response = await ApiClient.dio.get(
        '/thcs/$maTHCS/hocsinh/search',
        queryParameters: {'keyword': keyword},
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getHocSinhDetail({required String maHS}) async {
    try {
      final response = await ApiClient.dio.get('/thcs/hocsinh/$maHS');
      return response;
    } catch (e) {
      rethrow;
    }
  }
}
