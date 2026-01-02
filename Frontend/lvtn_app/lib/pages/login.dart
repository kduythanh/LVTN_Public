import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:lvtn_app/provider/auth_provider.dart';
import 'package:lvtn_app/theme/app_colors.dart';
import 'package:provider/provider.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'package:flutter/services.dart' show rootBundle;

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _isHuman = false;
  String? _recaptchaToken;
  bool _isLoading = false;
  late final WebViewController _controller;
  String _recaptchaHtmlContent = '';

  @override
  void initState() {
    super.initState();
    // Khởi tạo WebViewController trong initState
    _controller = _createWebViewController();
    _loadRecaptchaHtml();
  }

  Future<void> _loadRecaptchaHtml() async {
    try {
      final html = await rootBundle.loadString('assets/recaptcha.html');
      setState(() {
        _recaptchaHtmlContent = html;
      });
      debugPrint("Recaptcha HTML content loaded.");
    } catch (e) {
      debugPrint("ERROR loading recaptcha.html: $e");
    }
  }

  // Hàm tạo và cấu hình WebViewController (Đã sửa lỗi tham số)
  WebViewController _createWebViewController() {
    final controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setNavigationDelegate(
        NavigationDelegate(
          onProgress: (int progress) {
            debugPrint('WebView loading (progress: $progress%)');
          },
          onPageFinished: (String url) {
            debugPrint('Page finished loading: $url');
          },
        ),
      )
      // Thiết lập Kênh giao tiếp JavaScript để nhận Token
      ..addJavaScriptChannel(
        'RecaptchaChannel', // Tên Channel (Phải KHỚP với tên trong assets/recaptcha.html)
        onMessageReceived: (JavaScriptMessage message) {
          final token = message.message;
          debugPrint("Recaptcha token nhận được từ Channel: $token");

          if (token.isNotEmpty) {
            setState(() {
              _isHuman = true;
              _recaptchaToken = token;
            });
            // Đóng Modal sau khi nhận được token thành công
            Navigator.pop(context);
          }
        },
      );
    return controller;
  }

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  void _showRecaptchaVerification() {
    FocusScope.of(context).unfocus();
    setState(() {
      _isHuman = false;
      _recaptchaToken = null;
    });

    if (_recaptchaHtmlContent.isEmpty) {
      // Xử lý lỗi nếu không đọc được HTML
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Không thể tải nội dung reCAPTCHA.'),
          backgroundColor: AppColors.danger,
        ),
      );
      return;
    }

    // Sử dụng loadHtmlString và baseUrl
    _controller.loadHtmlString(
      _recaptchaHtmlContent,
      baseUrl: 'http://127.0.0.1',
    );

    showModalBottomSheet(
      isDismissible: false,
      backgroundColor: Colors.white,
      isScrollControlled: true,
      context: context,
      builder: (BuildContext context) {
        return SizedBox(
          height: MediaQuery.of(context).size.height * 0.8,
          child: Column(
            children: [
              Align(
                alignment: Alignment.topRight,
                child: IconButton(
                  icon: const Icon(Icons.close),
                  // Nút đóng sẽ reset trạng thái reCAPTCHA (nếu cần)
                  onPressed: () {
                    setState(() {
                      _isHuman = false;
                      _recaptchaToken = null;
                    });
                    Navigator.pop(context);
                  },
                ),
              ),
              Expanded(
                // Sử dụng WebViewWidget để hiển thị reCAPTCHA
                child: WebViewWidget(controller: _controller),
              ),
            ],
          ),
        );
      },
    );
  }

  Future<void> _onLoginPressed() async {
    FocusScope.of(context).unfocus();
    if (!_formKey.currentState!.validate()) return;

    if (!_isHuman || _recaptchaToken == null || _recaptchaToken!.isEmpty) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Vui lòng xác thực reCAPTCHA trước khi đăng nhập!'),
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
      await authProvider.login(
        _usernameController.text,
        _passwordController.text,
        _recaptchaToken!,
      );
      // Chuyển trang theo role được cấp
      if (!mounted) return;
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
          context.go('/hocsinh');
          break;
        default:
          context.go('/');
          break;
      }
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Đăng nhập thành công!'),
          backgroundColor: AppColors.success,
        ),
      );
    } catch (e) {
      debugPrint('Lỗi khi đăng nhập: $e');
      if (e is DioException) {
        final response = e.response;
        final apiMessage =
            response?.data?['message'] ?? 'Lỗi không xác định từ server';
        if (!mounted) return;
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(apiMessage),
            backgroundColor: AppColors.danger,
          ),
        );
        return;
      }
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Lỗi khi đăng nhập: $e'),
          backgroundColor: AppColors.danger,
        ),
      );
    } finally {
      setState(() {
        _isLoading = false;
        _isHuman = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: SingleChildScrollView(
        child: Center(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Form(
              key: _formKey,
              child: Column(
                children: [
                  // các input box để đăng nhập
                  TextFormField(
                    controller: _usernameController,
                    decoration: const InputDecoration(
                      labelText: "Tên đăng nhập",
                      border: OutlineInputBorder(),
                      prefixIcon: Icon(Icons.person),
                    ),
                    validator: (value) => value == null || value.isEmpty
                        ? 'Vui lòng nhập tên đăng nhập!'
                        : null,
                  ),
                  const SizedBox(height: 12),
                  TextFormField(
                    controller: _passwordController,
                    decoration: const InputDecoration(
                      labelText: "Mật khẩu",
                      border: OutlineInputBorder(),
                      prefixIcon: Icon(Icons.lock),
                    ),
                    obscureText: true,
                    validator: (value) => value == null || value.isEmpty
                        ? 'Vui lòng nhập mật khẩu!'
                        : null,
                  ),
                  const SizedBox(height: 16),
                  ElevatedButton(
                    onPressed: _isHuman ? null : _showRecaptchaVerification,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _isHuman
                          ? AppColors.info
                          : AppColors.warning,
                      minimumSize: const Size.fromHeight(45),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(32),
                      ),
                    ),
                    child: Text(
                      _isHuman
                          ? 'Đã xác thực reCAPTCHA thành công!'
                          : 'Xác thực reCAPTCHA',
                      style: TextStyle(
                        color: Colors.black,
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),
                  ElevatedButton(
                    onPressed: _isLoading ? null : _onLoginPressed,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColors.success,
                      minimumSize: const Size.fromHeight(45),
                    ),
                    child: Text(
                      'Đăng nhập',
                      style: const TextStyle(
                        color: Colors.white,
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
