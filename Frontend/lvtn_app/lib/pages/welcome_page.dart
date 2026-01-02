import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:lvtn_app/provider/auth_provider.dart';
import 'package:lvtn_app/theme/app_colors.dart';
import 'package:provider/provider.dart';

class WelcomePage extends StatefulWidget {
  const WelcomePage({super.key});

  @override
  State<WelcomePage> createState() => _WelcomePageState();
}

class _WelcomePageState extends State<WelcomePage> {
  @override
  void initState() {
    super.initState();
    _checkLoginStatus();
  }

  Future<void> _checkLoginStatus() async {
    final authProvider = Provider.of<AuthProvider>(context, listen: false);
    await authProvider.tryAutoLogin();

    await Future.delayed(const Duration(seconds: 3));

    if (!mounted) return;

    if (authProvider.isLoggedIn && authProvider.user != null) {
      final user = authProvider.user!;
      final maLoaiTK = int.tryParse(user['maLoaiTK'].toString()) ?? -1;
      switch (maLoaiTK) {
        case 0:
          context.go('/admin');
          break;
        case 1:
          context.go('/sgddt');
          break;
        case 2:
          context.go('/thcs');
          break;
        case 3:
          context.go('/thpt');
          break;
        case 4:
          context.go('/thcs');
          break;
        default:
          context.go('/');
          break;
      }
    } else {
      context.go('/');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.success,
      body: SafeArea(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset('assets/images/SGDDT.png', width: 150, height: 150),
              SizedBox(height: 12),
              Text(
                textAlign: TextAlign.center,
                "SỞ GIÁO DỤC VÀ ĐÀO TẠO TP CẦN THƠ",
                style: TextStyle(color: Colors.white),
              ),
              SizedBox(height: 12),
              Text(
                textAlign: TextAlign.center,
                "HỆ THỐNG QUẢN LÝ TUYỂN SINH LỚP 10 THPT TRỰC TUYẾN",
                style: TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 20,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
