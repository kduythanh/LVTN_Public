import 'package:flutter/material.dart';
import 'package:lvtn_app/api/common_api.dart';
import 'package:lvtn_app/models/chitieu.dart';
import 'package:lvtn_app/models/chitieu_chuyen.dart';
import 'package:lvtn_app/theme/app_colors.dart';

class AdmissionInfo extends StatelessWidget {
  const AdmissionInfo({super.key});

  @override
  Widget build(BuildContext context) {
    return SafeArea(child: AdmissionFullContent());
  }
}

class AdmissionFullContent extends StatefulWidget {
  const AdmissionFullContent({super.key});

  @override
  State<AdmissionFullContent> createState() => _AdmissionFullContentState();
}

class _AdmissionFullContentState extends State<AdmissionFullContent> {
  bool isLoading = true;
  List<ChiTieu> chiTieu = [];
  List<ChiTieuChuyen> chiTieuChuyen = [];
  List<ChiTieuChuyen> c1 = [];
  List<ChiTieuChuyen> c2 = [];
  List<ChiTieuChuyen> c3 = [];

  @override
  void initState() {
    super.initState();
    fetchChiTieu();
  }

  Future<void> fetchChiTieu() async {
    try {
      final resTHPT = await CommonApi.getChiTieu();
      final resChuyen = await CommonApi.getChiTieuChuyen();
      final dataTHPT = (resTHPT.data['data'] as List)
          .map((e) => ChiTieu.fromJson(e))
          .toList();
      final dataChuyen = (resChuyen.data['data'] as List)
          .map((e) => ChiTieuChuyen.fromJson(e))
          .toList();
      c1 = dataChuyen.where((e) => e.maTHPT == "92000F01").toList();
      c2 = dataChuyen.where((e) => e.maTHPT == "93000F16").toList();
      c3 = dataChuyen.where((e) => e.maTHPT == "94000706").toList();
      setState(() {
        chiTieu = dataTHPT;
        chiTieuChuyen = dataChuyen;
        isLoading = false;
      });
    } catch (error) {
      debugPrint("Lỗi khi lấy danh sách chỉ tiêu: $error");
      setState(() {
        isLoading = false;
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
          child: Column(
            children: [
              const SizedBox(height: 8.0),
              Text(
                "I. CÁC TRƯỜNG THPT CÔNG LẬP",
                style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold),
              ),
              Text(
                "(chỉ tiêu các trường THPT chuyên xem phía dưới)",
                style: TextStyle(fontSize: 18, fontStyle: FontStyle.italic),
              ),
              AdmissionContent(listTHPT: chiTieu),
              const SizedBox(height: 16.0),
              Text(
                "II. CÁC TRƯỜNG THPT CHUYÊN",
                style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold),
              ),
              AdmissionListChuyen(listC1: c1, listC2: c2, listC3: c3),
            ],
          ),
        ),
      ),
    );
  }
}

class AdmissionContent extends StatelessWidget {
  final List<ChiTieu> listTHPT;
  const AdmissionContent({super.key, required this.listTHPT});

  @override
  Widget build(BuildContext context) {
    final screenWidth = MediaQuery.of(context).size.width;
    const double padding = 6.0;
    final double itemWidth = (screenWidth - 2 * padding);
    return Column(
      children: [
        Wrap(
          spacing: 0,
          runSpacing: 6.0,
          children: listTHPT
              .map(
                (thpt) => SizedBox(
                  width: itemWidth,
                  child: THPTCard(thpt: thpt),
                ),
              )
              .toList(),
        ),
      ],
    );
  }
}

class THPTCard extends StatelessWidget {
  final ChiTieu thpt;
  const THPTCard({super.key, required this.thpt});

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 3,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      clipBehavior: Clip.antiAlias,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          ColoredBox(
            color: AppColors.success,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                thpt.tenTHPT,
                textAlign: TextAlign.center,
                style: const TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 18,
                  color: Colors.white,
                ),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text.rich(
              TextSpan(
                children: [
                  TextSpan(
                    text: "Địa chỉ: ",
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                  TextSpan(text: thpt.diaChi),
                ],
              ),
            ),
          ),
          ColoredBox(
            color: Colors.greenAccent.shade100,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text.rich(
                TextSpan(
                  children: [
                    TextSpan(text: "Số lượng đăng ký: "),
                    TextSpan(
                      text: "${thpt.soLuongDangKy}/${thpt.chiTieu}",
                      style: TextStyle(fontWeight: FontWeight.bold),
                    ),
                    TextSpan(text: " chỉ tiêu"),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class AdmissionListChuyen extends StatelessWidget {
  final List<ChiTieuChuyen> listC1, listC2, listC3;
  const AdmissionListChuyen({
    super.key,
    required this.listC1,
    required this.listC2,
    required this.listC3,
  });

  @override
  Widget build(BuildContext context) {
    final screenWidth = MediaQuery.of(context).size.width;
    const double spacing = 6.0;
    const double padding = 6.0;
    final double itemWidth = (screenWidth - spacing - 2 * padding) / 2;
    return Column(
      children: [
        const SizedBox(height: 8.0),
        const Text(
          "Trường THPT Chuyên Lý Tự Trọng",
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        Wrap(
          spacing: spacing,
          runSpacing: spacing,
          children: listC1
              .map(
                (lc) => SizedBox(
                  width: itemWidth,
                  child: ChuyenCard(
                    lc: lc,
                    bgColor: AppColors.primary,
                    textColor: Colors.white,
                  ),
                ),
              )
              .toList(),
        ),
        const SizedBox(height: 8.0),
        const Text(
          "Trường THPT Chuyên Vị Thanh",
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        Wrap(
          spacing: spacing,
          runSpacing: spacing,
          children: listC2
              .map(
                (lc) => SizedBox(
                  width: itemWidth,
                  child: ChuyenCard(lc: lc, bgColor: AppColors.info),
                ),
              )
              .toList(),
        ),
        const SizedBox(height: 8.0),
        const Text(
          "Trường THPT Chuyên Nguyễn Thị Minh Khai",
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        Wrap(
          spacing: spacing,
          runSpacing: spacing,
          children: listC3
              .map(
                (lc) => SizedBox(
                  width: itemWidth,
                  child: ChuyenCard(lc: lc, bgColor: AppColors.warning),
                ),
              )
              .toList(),
        ),
      ],
    );
  }
}

class ChuyenCard extends StatelessWidget {
  final ChiTieuChuyen lc;
  final Color bgColor;
  final Color? textColor;
  const ChuyenCard({
    super.key,
    required this.lc,
    required this.bgColor,
    this.textColor,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      clipBehavior: Clip.antiAlias,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          ColoredBox(
            color: bgColor,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                lc.tenLopChuyen,
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  color: textColor,
                  fontSize: 16,
                ),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text.rich(
              TextSpan(
                children: [
                  TextSpan(text: "Đăng ký: "),
                  TextSpan(
                    text: "${lc.soLuongDangKy}/${lc.chiTieu}",
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                  TextSpan(text: " chỉ tiêu"),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
