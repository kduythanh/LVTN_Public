import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:lvtn_app/api/common_api.dart';
import 'package:lvtn_app/models/thisinh_result.dart';
import 'package:lvtn_app/theme/app_colors.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'package:flutter/services.dart' show rootBundle;

class SearchResult extends StatefulWidget {
  const SearchResult({super.key});

  @override
  State<SearchResult> createState() => _SearchResultState();
}

class _SearchResultState extends State<SearchResult> {
  final _formKey = GlobalKey<FormState>();
  final _noiDungController = TextEditingController();
  List<dynamic> _listTHPT = [];
  String _selectedTHPT = "";
  int _selectedLoaiTC = 0;
  bool _isHuman = false;
  String? _recaptchaToken;
  bool _isLoading = false;
  List<ThiSinhResult> _listKQ = [];
  ThiSinhResult? _tsDetail;
  String? _message;
  bool _isError = false;
  late final WebViewController _controller;
  String _recaptchaHtmlContent = '';

  @override
  void initState() {
    super.initState();
    _controller = _createWebViewController();
    _loadRecaptchaHtml();
    _fetchTHPT();
  }

  Future<void> _loadRecaptchaHtml() async {
    try {
      final html = await rootBundle.loadString('assets/recaptcha.html');
      setState(() {
        _recaptchaHtmlContent = html;
      });
      debugPrint("Recaptcha HTML content loaded for Search page.");
    } catch (e) {
      debugPrint("ERROR loading recaptcha.html for Search page: $e");
    }
  }

  WebViewController _createWebViewController() {
    final controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setNavigationDelegate(
        NavigationDelegate(
          onProgress: (int progress) {
            debugPrint('Search WebView loading (progress: $progress%)');
          },
          onPageFinished: (String url) {
            debugPrint('Search Page finished loading: $url');
          },
        ),
      )
      // Thiết lập Kênh giao tiếp JavaScript để nhận Token
      ..addJavaScriptChannel(
        'RecaptchaChannel', // Tên Channel (Phải KHỚP với tên trong assets/recaptcha.html)
        onMessageReceived: (JavaScriptMessage message) {
          final token = message.message;
          debugPrint("Search Recaptcha token nhận được từ Channel: $token");

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

  Future<void> _fetchTHPT() async {
    try {
      final res = await CommonApi.getTHPT();
      setState(() {
        _listTHPT = res.data['data'] ?? [];
      });
    } catch (error) {
      debugPrint("Lỗi khi lấy danh sách THPT: $error");
    }
  }

  void _showRecaptchaVerification() {
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

    // *** THAY ĐỔI QUAN TRỌNG: Sử dụng loadHtmlString và baseUrl ***
    _controller.loadHtmlString(
      _recaptchaHtmlContent,
      // Thiết lập baseURL thành một tên miền HỢP LỆ đã được đăng ký
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
                  onPressed: () => Navigator.pop(context),
                ),
              ),
              Expanded(child: WebViewWidget(controller: _controller)),
            ],
          ),
        );
      },
    );
  }

  void _onSubmit() async {
    if (!_formKey.currentState!.validate()) return;
    if (!_isHuman || _recaptchaToken == null || _recaptchaToken!.isEmpty) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Vui lòng xác thực reCAPTCHA trước khi tìm kiếm!'),
          backgroundColor: AppColors.danger,
        ),
      );
      return;
    }
    setState(() {
      _isLoading = true;
    });
    try {
      final result = await CommonApi.getKetQuaThi(
        hoiDongThi: _selectedTHPT,
        loaiTC: _selectedLoaiTC,
        noiDungTC: _noiDungController.text,
        captchaToken: _recaptchaToken!,
      );
      debugPrint("Dữ liệu JSON nhận về:");
      debugPrint(result.data.toString());
      final data = result.data['data'] as List<dynamic>? ?? [];
      final kqList = data.map((e) => ThiSinhResult.fromJson(e)).toList();
      if (kqList.isEmpty) {
        if (!mounted) return;
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Không tìm thấy thí sinh thỏa mãn!'),
            backgroundColor: AppColors.warning,
          ),
        );

        setState(() {
          _listKQ = [];
          _tsDetail = null;
          _message = 'Không tìm thấy thí sinh thỏa mãn!';
          _isError = true;
        });

        return;
      }
      setState(() {
        _listKQ = kqList;
        _message = null;
        _isError = false;

        if (kqList.length == 1) {
          _tsDetail = kqList.first;
        } else {
          _tsDetail = null;
        }
      });
    } catch (e) {
      debugPrint('Lỗi khi tra cứu kết quả: $e');
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

        setState(() {
          _listKQ = [];
          _tsDetail = null;
          _message = apiMessage; // đặt vào màn hình
          _isError = true;
        });

        return;
      }
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Lỗi khi tra cứu: $e'),
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
          child: Form(
            key: _formKey,
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                children: [
                  Text(
                    "KỲ THI TUYỂN SINH LỚP 10 THPT",
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                  ),
                  Text("Năm học 2026-2027", style: TextStyle(fontSize: 18)),
                  const SizedBox(height: 24),
                  // Select hội đồng thi
                  DropdownButtonFormField<String>(
                    initialValue: _selectedTHPT,
                    decoration: const InputDecoration(
                      labelText: "Chọn hội đồng thi",
                      border: OutlineInputBorder(),
                    ),
                    items: [
                      const DropdownMenuItem(
                        value: "",
                        child: Text("Chọn hội đồng thi"),
                      ),
                      ..._listTHPT.map((thpt) {
                        return DropdownMenuItem<String>(
                          value: thpt['maTHPT'].toString(),
                          child: Text(thpt['tenTHPT']),
                        );
                      }),
                    ],
                    onChanged: (value) =>
                        setState(() => _selectedTHPT = value!),
                    validator: (value) =>
                        (value == "") ? 'Vui lòng chọn hội đồng thi!' : null,
                  ),
                  const SizedBox(height: 12),
                  // Select loại tra cứu
                  DropdownButtonFormField<int>(
                    initialValue: 0,
                    decoration: const InputDecoration(
                      labelText: "Chọn loại tra cứu",
                      border: OutlineInputBorder(),
                    ),
                    items: const [
                      DropdownMenuItem(
                        value: 0,
                        child: Text("Chọn loại tra cứu"),
                      ),
                      DropdownMenuItem(
                        value: 1,
                        child: Text("Theo số báo danh"),
                      ),
                      DropdownMenuItem(
                        value: 2,
                        child: Text("Theo mã học sinh"),
                      ),
                      DropdownMenuItem(value: 3, child: Text("Theo họ và tên")),
                    ],
                    onChanged: (value) =>
                        setState(() => _selectedLoaiTC = value!),
                    validator: (value) =>
                        (value == 0) ? 'Vui lòng chọn loại tra cứu!' : null,
                  ),
                  const SizedBox(height: 12),
                  // Textbox nhập nội dung tra cứu
                  TextFormField(
                    controller: _noiDungController,
                    decoration: const InputDecoration(
                      labelText: "Thông tin tra cứu",
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) => value == null || value.isEmpty
                        ? 'Vui lòng nhập thông tin tra cứu!'
                        : null,
                  ),
                  const SizedBox(height: 12),
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
                  const SizedBox(height: 12),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColors.success,
                      minimumSize: const Size.fromHeight(45),
                    ),
                    onPressed: _onSubmit,
                    child: const Text(
                      "TRA CỨU KẾT QUẢ",
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  const SizedBox(height: 12),
                  if (_isLoading) ...[
                    const CircularProgressIndicator(),
                  ] else if (_isError && _message != null) ...[
                    Text(_message!, style: const TextStyle(color: Colors.red)),
                  ] else if (_listKQ.isNotEmpty && _tsDetail == null) ...[
                    ThiSinhList(
                      list: _listKQ,
                      onSelect: (ts) => setState(() => _tsDetail = ts),
                    ),
                  ] else if (_tsDetail != null) ...[
                    ThiSinhDetail(data: _tsDetail!),
                  ],
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class ThiSinhList extends StatelessWidget {
  final List<ThiSinhResult> list;
  final Function(ThiSinhResult) onSelect;

  const ThiSinhList({super.key, required this.list, required this.onSelect});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          Text(
            "Tìm thấy ${list.length} thí sinh thỏa mãn.",
            style: const TextStyle(fontWeight: FontWeight.bold),
          ),
          Text(
            "Vui lòng chọn 1 thí sinh để xem chi tiết:",
            style: const TextStyle(fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 4),
          ListView.builder(
            itemCount: list.length,
            shrinkWrap: true,
            physics: const NeverScrollableScrollPhysics(),
            itemBuilder: (context, index) {
              final ts = list[index];
              return Card(
                elevation: 3,
                margin: const EdgeInsets.symmetric(vertical: 8),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                child: ListTile(
                  contentPadding: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 8,
                  ),
                  title: Text(
                    "${ts.hoVaChuLotHS} ${ts.tenHS}",
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 16,
                    ),
                  ),
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text("MSHS: ${ts.maHS}"),
                      Text("SBD: ${ts.soBaoDanh}"),
                      Text("Trường ${ts.tenTHCS}"),
                    ],
                  ),
                  trailing: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColors.primary,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                    onPressed: () => onSelect(ts),
                    child: const Text(
                      "Xem",
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}

class ThiSinhDetail extends StatelessWidget {
  final ThiSinhResult data;
  const ThiSinhDetail({super.key, required this.data});

  @override
  Widget build(BuildContext context) {
    final tongDiem =
        (data.diemToan ?? 0) +
        (data.diemVan ?? 0) +
        (data.diemMonThu3 ?? 0) +
        (data.diemCongUuTien ?? 0) +
        (data.diemCongKhuyenKhich ?? 0);

    final tongDiemChuyen = data.diemMonChuyen != null
        ? (data.diemToan ?? 0) +
              (data.diemVan ?? 0) +
              (data.diemMonThu3 ?? 0) +
              2 * (data.diemMonChuyen ?? 0) +
              (data.diemCongKhuyenKhichChuyen ?? 0)
        : null;

    return Card(
      margin: const EdgeInsets.all(8),
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Text(
              "${data.hoVaChuLotHS} ${data.tenHS}",
              style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
            ),
            Text("Trường ${data.tenTHCS}"),
            const Divider(),
            Text("Điểm môn Toán: ${data.diemToan?.toStringAsFixed(2)}"),
            Text("Điểm môn Ngữ văn: ${data.diemVan?.toStringAsFixed(2)}"),
            Text("Điểm môn thứ 3: ${data.diemMonThu3?.toStringAsFixed(2)}"),
            Text(
              "Điểm cộng ưu tiên: ${data.diemCongUuTien?.toStringAsFixed(2)}",
            ),
            Text(
              "Điểm cộng khuyến khích: ${data.diemCongKhuyenKhich?.toStringAsFixed(2)}",
            ),
            const SizedBox(height: 8),
            Text(
              "Tổng điểm: ${tongDiem.toStringAsFixed(2)}",
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
            if (tongDiemChuyen != null)
              Column(
                children: [
                  const Divider(),
                  Text(
                    "Điểm cộng khuyến khích (chuyên): ${data.diemCongKhuyenKhichChuyen?.toStringAsFixed(2)}",
                  ),
                  Text(
                    "Tổng điểm chuyên: ${tongDiemChuyen.toStringAsFixed(2)}",
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                ],
              ),

            if (data.nguyenVongDau != null)
              Column(
                children: [
                  const Divider(),
                  Text(
                    "Chúc mừng bạn đã trúng tuyển",
                    textAlign: TextAlign.center,
                    style: const TextStyle(color: AppColors.success),
                  ),
                  Text(
                    "NV${data.nguyenVongDau} - Trường ${data.truongTHPTDau}",
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                      color: AppColors.success,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              )
            else
              Column(
                children: [
                  const Divider(),
                  Text(
                    "Rất tiếc, bạn chưa trúng tuyển nguyện vọng nào.",
                    textAlign: TextAlign.center,
                    style: TextStyle(color: AppColors.danger),
                  ),
                  Text(
                    "Nếu có nhu cầu phúc khảo, vui lòng liên hệ hội đồng thi nơi bạn dự thi để được hướng dẫn chi tiết.",
                    textAlign: TextAlign.center,
                    style: TextStyle(color: AppColors.danger),
                  ),
                ],
              ),
          ],
        ),
      ),
    );
  }
}
