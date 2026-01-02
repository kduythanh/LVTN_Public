import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:syncfusion_flutter_core/theme.dart';
import 'package:syncfusion_flutter_pdfviewer/pdfviewer.dart';
import 'package:path_provider/path_provider.dart';
import 'package:open_filex/open_filex.dart';

class Information extends StatefulWidget {
  const Information({super.key});

  @override
  State<Information> createState() => _InformationState();
}

class _InformationState extends State<Information> {
  String? selectedSrc;

  final documents = [
    {
      "src": "assets/files/QDUB_40_0001.pdf",
      "content":
          "Quyết định của UBND TP Cần Thơ phê duyệt Kế hoạch tuyển sinh vào lớp 10",
    },
    {
      "src": "assets/files/HuongDanTuyenSinh.pdf",
      "content": "Hướng dẫn tuyển sinh của Sở Giáo dục và Đào tạo TP Cần Thơ",
    },
    {
      "src": "assets/files/QuyDinhTuyenSinh10_2024.pdf",
      "content": "Quy định thi tuyển sinh vào lớp 10 THPT",
    },
    {
      "src": "assets/files/ChiTieuTuyenSinh2024.pdf",
      "content": "Chỉ tiêu tuyển sinh dự kiến của các trường THPT",
    },
    {
      "src": "assets/files/PhieuDangKy2024.pdf",
      "content": "Phiếu đăng ký tuyển sinh vào lớp 10",
    },
    {
      "src": "assets/files/DonPhucKhao.pdf",
      "content": "Đơn xin phúc khảo bài thi",
    },
  ];

  Future<String?> _openAssetPdfExternally(String assetPath) async {
    try {
      final byteData = await rootBundle.load(assetPath);
      final buffer = byteData.buffer;
      final tempDir = await getTemporaryDirectory();

      final fileName = assetPath.split('/').last;
      final filePath = '${tempDir.path}/$fileName';

      await File(filePath).writeAsBytes(
        buffer.asUint8List(byteData.offsetInBytes, byteData.lengthInBytes),
      );

      return filePath;
    } catch (e) {
      debugPrint("Lỗi khi lưu tệp PDF: $e");
      return null;
    }
  }

  @override
  Widget build(BuildContext context) {
    final ColorScheme colorScheme = Theme.of(context).colorScheme;
    return SafeArea(
      child: SingleChildScrollView(
        child: Center(
          child: Column(
            children: [
              const SizedBox(height: 8.0),
              const Text(
                "Bấm chọn các văn bản sau để xem chi tiết:",
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 12),
              ...documents.map((doc) {
                final src = doc["src"]!;
                final content = doc["content"]!;
                final isSelected = selectedSrc == src;
                return Card(
                  color: isSelected ? Colors.green.shade100 : null,
                  child: ListTile(
                    title: Text(content),
                    trailing: const Icon(
                      Icons.picture_as_pdf,
                      color: Colors.red,
                    ),
                    onTap: () {
                      setState(() => selectedSrc = isSelected ? null : src);
                    },
                  ),
                );
              }),
              const SizedBox(height: 12),
              if (selectedSrc != null)
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 8.0),
                      child: Row(
                        children: [
                          const Text(
                            "Chi tiết văn bản: ",
                            style: TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          OutlinedButton.icon(
                            icon: const Icon(Icons.open_in_new),
                            label: const Text("Mở bằng ứng dụng khác"),
                            onPressed: () async {
                              if (selectedSrc == null) return;
                              final filePath = await _openAssetPdfExternally(
                                selectedSrc!,
                              );
                              if (filePath != null) {
                                final result = await OpenFilex.open(filePath);
                                if (result.type != ResultType.done) {
                                  if (!context.mounted) return;
                                  ScaffoldMessenger.of(context).showSnackBar(
                                    SnackBar(
                                      content: Text(
                                        'Không thể mở tệp: ${result.message}',
                                      ),
                                    ),
                                  );
                                }
                              } else {
                                if (!context.mounted) return;
                                ScaffoldMessenger.of(context).showSnackBar(
                                  const SnackBar(
                                    content: Text('Lỗi khi chuẩn bị tệp để mở'),
                                  ),
                                );
                              }
                            },
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 8),
                    SizedBox(
                      height: 500,
                      child: SfPdfViewerTheme(
                        data: SfPdfViewerThemeData(
                          backgroundColor: colorScheme.surface,
                          scrollHeadStyle: PdfScrollHeadStyle(
                            backgroundColor: colorScheme.surface,
                            pageNumberTextStyle: TextStyle(
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          paginationDialogStyle: PdfPaginationDialogStyle(
                            backgroundColor: colorScheme.surface,
                          ),
                        ),
                        child: SfPdfViewer.asset(selectedSrc!),
                      ),
                    ),
                    const SizedBox(height: 8),
                  ],
                ),
            ],
          ),
        ),
      ),
    );
  }
}
