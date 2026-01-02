import 'dart:typed_data';

import 'package:dio/dio.dart';
import 'package:flutter/widgets.dart';
import 'api_client.dart';

class CommonApi {
  static final Dio _dio = Dio(BaseOptions(baseUrl: 'http://10.0.2.2:8080/api'));

  static Future<Response> login({
    required String username,
    required String password,
    required String recaptchaToken,
  }) async {
    FormData formData = FormData.fromMap({
      'tenTK': username,
      'matKhau': password,
      'captchaToken': recaptchaToken,
    });
    try {
      final response = await ApiClient.dio.post(
        '/common/login',
        data: formData,
        options: Options(contentType: 'multipart/form-data'),
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> logout() async {
    try {
      final response = await ApiClient.dio.post('/common/logout');
      ApiClient.setAuthToken('');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getTHPT() async {
    try {
      final response = await ApiClient.dio.get('/common/thpt');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getTHCS() async {
    try {
      final response = await ApiClient.dio.get('/common/thcs');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getKetQuaThi({
    required String hoiDongThi,
    required int loaiTC,
    required String noiDungTC,
    required String captchaToken,
  }) async {
    try {
      final response = await ApiClient.dio.get(
        '/common/ketquathi',
        queryParameters: {
          'maTHPT': hoiDongThi,
          'type': loaiTC,
          'keyword': noiDungTC,
        },
        options: Options(headers: {'X-Captcha-Token': captchaToken}),
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getKQTBySBD({required String soBaoDanh}) async {
    try {
      final response = await ApiClient.dio.get('/common/ketquathi/$soBaoDanh');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getChiTieu() async {
    try {
      final response = await ApiClient.dio.get('/common/thpt/chitieu');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> getChiTieuChuyen() async {
    try {
      final response = await ApiClient.dio.get('/common/thpt/chitieu-chuyen');
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Response> changePassword({
    required String tenTK,
    required String oldPassword,
    required String newPassword,
    required String confirmNewPassword,
  }) async {
    FormData formData = FormData.fromMap({
      'oldPassword': oldPassword,
      'newPassword': newPassword,
      'confirmNewPassword': confirmNewPassword,
    });
    try {
      final response = await ApiClient.dio.post(
        '/common/password/$tenTK',
        data: formData,
        options: Options(contentType: 'multipart/form-data'),
      );
      return response;
    } catch (e) {
      rethrow;
    }
  }

  static Future<Uint8List?> getAnhHocSinh(String maHS) async {
    try {
      final response = await _dio.get<List<int>>(
        '/common/anh/$maHS',
        options: Options(responseType: ResponseType.bytes),
      );
      return Uint8List.fromList(response.data!);
    } catch (e) {
      debugPrint('Lỗi tải ảnh học sinh: $e');
      return null;
    }
  }
}
