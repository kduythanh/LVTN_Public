import 'package:dio/dio.dart';
import 'package:lvtn_app/api/api_client.dart';

class AdminApi {
  static Future<Response> getAccount() async {
    try {
      final response = await ApiClient.dio.get('/admin/account');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> searchAccount({required String keyword}) async {
    try {
      final response = await ApiClient.dio.get(
        '/admin/account/search',
        queryParameters: {'keyword': keyword},
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }
}
