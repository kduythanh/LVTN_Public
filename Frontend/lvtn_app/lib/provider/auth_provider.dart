import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:lvtn_app/api/api_client.dart';
import 'package:lvtn_app/api/common_api.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AuthProvider extends ChangeNotifier {
  String? _token;
  Map<String, dynamic>? _user;

  String? get token => _token;
  Map<String, dynamic>? get user => _user;
  bool get isLoggedIn => _token != null;

  Future<void> tryAutoLogin() async {
    final prefs = await SharedPreferences.getInstance();
    final savedToken = prefs.getString('auth_token');
    final savedUser = prefs.getString('auth_user');

    if (savedToken != null && savedUser != null) {
      _token = savedToken;
      _user = jsonDecode(savedUser) as Map<String, dynamic>;
      ApiClient.setAuthToken(_token!);
      notifyListeners();
    }
  }

  Future<void> login(
    String username,
    String password,
    String recaptchaToken,
  ) async {
    try {
      final response = await CommonApi.login(
        username: username,
        password: password,
        recaptchaToken: recaptchaToken,
      );
      // Parse dữ liệu
      Map<String, dynamic> jsonData;
      if (response.data is String) {
        jsonData = jsonDecode(response.data);
      } else {
        jsonData = response.data as Map<String, dynamic>;
      }
      final token = jsonData['data']['token'] as String;
      Map<String, dynamic> user =
          jsonData['data']['user'] as Map<String, dynamic>;
      // Truyền thông tin bổ sung
      if (user['maLoaiTK'].toString() == '3') {
        try {
          final resTHPT = await CommonApi.getTHPT();
          final listTHPT = resTHPT.data['data'] as List<dynamic>;
          final infoTHPT = listTHPT.firstWhere(
            (item) => item['maTHPT'] == user['soDinhDanh'],
            orElse: () => null,
          );
          if (infoTHPT != null) {
            user['thptChuyen'] = infoTHPT['thptChuyen'];
            user['tsNgoaiTPCT'] = infoTHPT['tsNgoaiTPCT'];
          }
        } catch (err) {
          debugPrint("Không thể lấy thông tin trường THPT: $err");
        }
      }
      // Lưu vào memory
      _token = token;
      _user = user;
      // Lưu vào SharedPreferences (giống LocalStorage bên web)
      try {
        final prefs = await SharedPreferences.getInstance();
        await prefs.setString('auth_token', _token!);
        await prefs.setString('auth_user', jsonEncode(_user));
      } catch (e, stack) {
        debugPrint('Lỗi khi lưu SharedPreferences: $e');
        debugPrint(stack.toString());
      }
      // Cập nhật header Dio
      ApiClient.setAuthToken(_token!);
      notifyListeners();
    } on DioException catch (e) {
      debugPrint('DioException: ${e.response?.data}');
      rethrow;
    }
  }

  Future<void> logout() async {
    try {
      await CommonApi.logout();
    } catch (err) {
      debugPrint("Lỗi khi logout: $err");
    }

    try {
      final prefs = await SharedPreferences.getInstance();
      await prefs.remove('auth_token'); // xóa token
      await prefs.remove('auth_user'); // xóa user
    } catch (e, stack) {
      debugPrint('Lỗi khi cập nhật SharedPreferences: $e');
      debugPrint(stack.toString());
    }

    _token = null;
    _user = null;

    ApiClient.setAuthToken('');
    await Future.delayed(Duration.zero);
    notifyListeners();
  }

  String getLoaiTK(int maLoaiTK) {
    switch (maLoaiTK) {
      case 0:
        return "Quản trị viên";
      case 1:
        return "Hội đồng tuyển sinh";
      case 2:
        return "Giáo viên THCS";
      case 3:
        return "Giáo viên THPT";
      case 4:
        return "Học sinh";
      default:
        return "Không xác định";
    }
  }
}
