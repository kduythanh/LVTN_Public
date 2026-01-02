import 'package:dio/dio.dart';
import 'package:lvtn_app/api/api_client.dart';

class HocSinhApi {
  static Future<Response> getHocSinhDetail({required String maHS}) async {
    try {
      final response = await ApiClient.dio.get('/hocsinh/$maHS');
      return response;
    } catch (e) {
      rethrow;
    }
  }
}
