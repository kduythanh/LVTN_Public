import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:lvtn_app/api/common_api.dart';
import 'package:lvtn_app/provider/auth_provider.dart';
import 'package:lvtn_app/theme/app_colors.dart';
import 'package:provider/provider.dart';

class THCSChangePassword extends StatefulWidget {
  const THCSChangePassword({super.key});

  @override
  State<THCSChangePassword> createState() => _THCSChangePasswordState();
}

class _THCSChangePasswordState extends State<THCSChangePassword> {
  final _formKey = GlobalKey<FormState>();
  final _currentPasswordController = TextEditingController();
  final _newPasswordController = TextEditingController();
  final _confirmNewPasswordController = TextEditingController();
  bool _isLoading = false;

  Future<void> _onChangePasswordPressed() async {
    if (!_formKey.currentState!.validate()) return;

    String currentPassword = _currentPasswordController.text;
    String newPassword = _newPasswordController.text;
    String confirmNewPassword = _confirmNewPasswordController.text;
    if (currentPassword == newPassword) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Mật khẩu mới không được phép trùng với mật khẩu cũ!'),
          backgroundColor: AppColors.danger,
        ),
      );
      return;
    }
    if (newPassword != confirmNewPassword) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            'Xác nhận mật khẩu mới phải trùng khớp với mật khẩu mới!',
          ),
          backgroundColor: AppColors.danger,
        ),
      );
      return;
    }
    setState(() {
      _isLoading = true;
    });
    try {
      final authProvider = Provider.of<AuthProvider>(context, listen: false);
      final user = authProvider.user ?? {};
      String tenTK = user['tenTK'];
      await CommonApi.changePassword(
        tenTK: tenTK,
        oldPassword: currentPassword,
        newPassword: newPassword,
        confirmNewPassword: confirmNewPassword,
      );
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Đổi mật khẩu thành công!'),
          backgroundColor: AppColors.success,
        ),
      );
      context.pop();
    } catch (e) {
      debugPrint('Lỗi khi đổi mật khẩu: $e');
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Đổi mật khẩu thất bại: $e'),
          backgroundColor: AppColors.danger,
        ),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: SingleChildScrollView(
        child: Center(
          child: Padding(
            padding: EdgeInsets.all(8.0),
            child: Form(
              key: _formKey,
              child: Column(
                children: [
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _currentPasswordController,
                    decoration: const InputDecoration(
                      labelText: "Mật khẩu hiện tại",
                      border: OutlineInputBorder(),
                    ),
                    obscureText: true,
                    validator: (value) => value == null || value.isEmpty
                        ? 'Vui lòng nhập mật khẩu hiện tại!'
                        : null,
                  ),
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _newPasswordController,
                    decoration: const InputDecoration(
                      labelText: "Mật khẩu mới",
                      border: OutlineInputBorder(),
                    ),
                    obscureText: true,
                    validator: (value) => value == null || value.isEmpty
                        ? 'Vui lòng nhập mật khẩu mới!'
                        : null,
                  ),
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _confirmNewPasswordController,
                    decoration: const InputDecoration(
                      labelText: "Xác nhận mật khẩu mới",
                      border: OutlineInputBorder(),
                    ),
                    obscureText: true,
                    validator: (value) => value == null || value.isEmpty
                        ? 'Vui lòng nhập xác nhận mật khẩu mới!'
                        : null,
                  ),
                  const SizedBox(height: 16),
                  ElevatedButton(
                    onPressed: _isLoading ? null : _onChangePasswordPressed,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColors.warning,
                      minimumSize: const Size.fromHeight(45),
                    ),
                    child: Text(
                      'Đổi mật khẩu',
                      style: const TextStyle(
                        color: Colors.black,
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),
                  if (_isLoading)
                    const SizedBox(
                      height: 24,
                      width: 24,
                      child: CircularProgressIndicator(
                        color: AppColors.success,
                        strokeWidth: 2,
                      ),
                    ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
