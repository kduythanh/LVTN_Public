import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:lvtn_app/api/sgddt_api.dart';
import 'package:lvtn_app/models/ketqua_thi.dart';
import 'package:lvtn_app/theme/app_colors.dart';

class HDTSUpdateScore extends StatefulWidget {
  final String sbd;
  const HDTSUpdateScore({super.key, required this.sbd});

  @override
  State<HDTSUpdateScore> createState() => _HDTSUpdateScoreState();
}

class _HDTSUpdateScoreState extends State<HDTSUpdateScore> {
  final _formKey = GlobalKey<FormState>();
  final _sbdController = TextEditingController();
  final _hoTenThiSinhController = TextEditingController();
  final _diemToanController = TextEditingController();
  final _diemVanController = TextEditingController();
  final _diemMonThu3Controller = TextEditingController();
  final _diemMonChuyenController = TextEditingController();
  KetQuaThi? ketQuaThi;
  bool isLoading = true;
  bool isUpdating = false;

  @override
  void initState() {
    super.initState();
    fetchKQThi();
  }

  @override
  void dispose() {
    super.dispose();
    _sbdController.dispose();
    _hoTenThiSinhController.dispose();
    _diemToanController.dispose();
    _diemVanController.dispose();
    _diemMonThu3Controller.dispose();
    _diemMonChuyenController.dispose();
  }

  Future<void> fetchKQThi() async {
    try {
      final res = await SGDDTApi.getKQThiBySBD(sbd: widget.sbd);
      final data = KetQuaThi.fromJson(res.data['data']);
      setState(() {
        ketQuaThi = data;
        _sbdController.text = data.soBaoDanh;
        _hoTenThiSinhController.text = "${data.hoVaChuLotHS} ${data.tenHS}";
        _diemToanController.text =
            data.diemToan?.toStringAsFixed(2).toString() ?? '';
        _diemVanController.text =
            data.diemVan?.toStringAsFixed(2).toString() ?? '';
        _diemMonThu3Controller.text =
            data.diemMonThu3?.toStringAsFixed(2).toString() ?? '';
        _diemMonChuyenController.text =
            data.diemMonChuyen?.toStringAsFixed(2).toString() ?? '';
        isLoading = false;
      });
    } catch (e) {
      debugPrint("Lỗi khi lấy thông tin thí sinh: $e");
      setState(() => isLoading = false);
    }
  }

  bool isValidScore(String? value) {
    if (value == null || value.trim().isEmpty) {
      return true;
    }
    final parsed = double.tryParse(value);
    if (parsed == null) return false;
    return parsed >= 0 && parsed <= 10;
  }

  Future<void> _onUpdateScorePressed() async {
    if (!_formKey.currentState!.validate()) return;

    String sbd = _sbdController.text;
    String maHS = ketQuaThi!.maHS;
    double? diemToan = double.tryParse(_diemToanController.text.trim());
    double? diemVan = double.tryParse(_diemVanController.text.trim());
    double? diemMonThu3 = double.tryParse(_diemMonThu3Controller.text.trim());
    double? diemMonChuyen = double.tryParse(
      _diemMonChuyenController.text.trim(),
    );

    if (!isValidScore(_diemToanController.text) ||
        !isValidScore(_diemVanController.text) ||
        !isValidScore(_diemMonThu3Controller.text) ||
        !isValidScore(_diemMonChuyenController.text)) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Điểm thi các môn phải từ 0.00 đến 10.00!'),
          backgroundColor: AppColors.danger,
        ),
      );
      return;
    }

    setState(() {
      isUpdating = true;
    });
    try {
      await SGDDTApi.updateKQThiBySBD(
        sbd: sbd,
        maHS: maHS,
        diemToan: diemToan,
        diemVan: diemVan,
        diemMonThu3: diemMonThu3,
        diemMonChuyen: diemMonChuyen,
      );
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Cập nhật điểm thí sinh thành công!'),
          backgroundColor: AppColors.success,
        ),
      );
      context.pop(true);
    } catch (e) {
      debugPrint('Lỗi khi cập nhật điểm: $e');
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Cập nhật điểm thất bại: $e'),
          backgroundColor: AppColors.danger,
        ),
      );
    } finally {
      setState(() {
        isUpdating = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return SafeArea(child: const Center(child: CircularProgressIndicator()));
    }
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
                    controller: _sbdController,
                    decoration: const InputDecoration(
                      labelText: "Số báo danh",
                      border: OutlineInputBorder(),
                    ),
                    readOnly: true,
                  ),
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _hoTenThiSinhController,
                    decoration: const InputDecoration(
                      labelText: "Họ và tên thí sinh",
                      border: OutlineInputBorder(),
                    ),
                    readOnly: true,
                  ),
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _diemToanController,
                    decoration: const InputDecoration(
                      labelText: "Điểm môn Toán",
                      border: OutlineInputBorder(),
                    ),
                  ),
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _diemVanController,
                    decoration: const InputDecoration(
                      labelText: "Điểm môn Ngữ Văn",
                      border: OutlineInputBorder(),
                    ),
                  ),
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _diemMonThu3Controller,
                    decoration: const InputDecoration(
                      labelText: "Điểm môn thứ 3",
                      border: OutlineInputBorder(),
                    ),
                  ),
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _diemMonChuyenController,
                    decoration: const InputDecoration(
                      labelText: "Điểm môn chuyên",
                      border: OutlineInputBorder(),
                    ),
                    readOnly: (ketQuaThi!.thptChuyen == false),
                    enabled: (ketQuaThi!.thptChuyen == true),
                  ),
                  const SizedBox(height: 16),
                  ElevatedButton(
                    onPressed: (isLoading || isUpdating)
                        ? null
                        : _onUpdateScorePressed,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColors.warning,
                      minimumSize: const Size.fromHeight(45),
                    ),
                    child: Text(
                      'Cập nhật điểm',
                      style: const TextStyle(
                        color: Colors.black,
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),
                  if (isUpdating)
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
