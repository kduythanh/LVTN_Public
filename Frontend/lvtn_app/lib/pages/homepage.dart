import 'package:flutter/material.dart';
import 'package:lvtn_app/theme/app_colors.dart';

class Homepage extends StatelessWidget {
  const Homepage({super.key});

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: SingleChildScrollView(
        child: Center(
          child: Column(
            children: [
              HomepageBackground(),
              SizedBox(height: 12),
              MocThoiGian(),
              SizedBox(height: 12),
              LichThi(),
            ],
          ),
        ),
      ),
    );
  }
}

class HomepageBackground extends StatelessWidget {
  const HomepageBackground({super.key});

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 250,
      width: double.infinity,
      child: Stack(
        alignment: Alignment.center,
        children: [
          Positioned.fill(
            child: Image.asset(
              'assets/images/background-pano.jpg',
              fit: BoxFit.cover,
            ),
          ),
          Positioned.fill(
            child: Container(color: Colors.black.withValues(alpha: 0.7)),
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                "KỲ THI TUYỂN SINH LỚP 10 THPT",
                style: TextStyle(
                  fontSize: 25,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
              Text(
                "NĂM HỌC 2026-2027",
                style: TextStyle(
                  fontSize: 25,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
              Text(
                "Dự kiến diễn ra vào tháng 6/2026",
                style: TextStyle(fontSize: 20, color: Colors.white),
              ),
            ],
          ),
        ],
      ),
    );
  }
}

class CardItem extends StatelessWidget {
  final String title;
  final String image;
  final String footer;
  final Color bgcolor;
  final Color textColor;

  const CardItem({
    super.key,
    required this.title,
    required this.image,
    required this.footer,
    required this.bgcolor,
    this.textColor = Colors.black,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      elevation: 4,
      clipBehavior: Clip.antiAlias,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          ColoredBox(
            color: bgcolor,
            child: Padding(
              padding: EdgeInsets.all(8),
              child: Text(
                title,
                textAlign: TextAlign.center,
                style: TextStyle(fontWeight: FontWeight.bold, color: textColor),
              ),
            ),
          ),
          AspectRatio(
            aspectRatio: 16 / 9,
            child: Image.asset(image, fit: BoxFit.cover),
          ),
          ColoredBox(
            color: bgcolor,
            child: Padding(
              padding: EdgeInsets.all(8),
              child: Text(
                footer,
                textAlign: TextAlign.center,
                style: TextStyle(fontWeight: FontWeight.bold, color: textColor),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class MocThoiGian extends StatelessWidget {
  const MocThoiGian({super.key});

  @override
  Widget build(BuildContext context) {
    final screenWidth = MediaQuery.of(context).size.width;
    final List<Map<String, dynamic>> items = [
      {
        "title": "Tháng 4/2026",
        "image": "assets/images/process-1.jpg",
        "footer": "Nhận hồ sơ đăng ký",
        "bgcolor": AppColors.primary,
        "textColor": Colors.white,
      },
      {
        "title": "Tháng 5/2026",
        "image": "assets/images/process-2.jpg",
        "footer": "Sửa nguyện vọng",
        "bgcolor": AppColors.warning,
        "textColor": Colors.black,
      },
      {
        "title": "Đầu tháng 6/2026",
        "image": "assets/images/process-3.jpg",
        "footer": "Thi tuyển",
        "bgcolor": AppColors.danger,
        "textColor": Colors.white,
      },
      {
        "title": "Giữa tháng 6/2026",
        "image": "assets/images/process-4.jpg",
        "footer": "Công bố kết quả",
        "bgcolor": AppColors.success,
        "textColor": Colors.white,
      },
    ];
    return Column(
      children: [
        Text(
          "CÁC MỐC THỜI GIAN QUAN TRỌNG",
          style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold),
        ),
        SizedBox(height: 12),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 6),
          child: Wrap(
            spacing: 6,
            runSpacing: 6,
            children: items.map((item) {
              return SizedBox(
                width: (screenWidth - 6 * 3) / 2,
                child: CardItem(
                  title: item["title"],
                  image: item["image"],
                  footer: item["footer"],
                  bgcolor: item["bgcolor"],
                  textColor: item["textColor"],
                ),
              );
            }).toList(),
          ),
        ),
      ],
    );
  }
}

class LichThi extends StatelessWidget {
  const LichThi({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Text(
          "LỊCH THI TUYỂN SINH LỚP 10 THPT",
          style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold),
        ),
        Text(
          "(dự kiến tổ chức đầu tháng 6/2026)",
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
            color: Colors.red,
          ),
        ),
        SizedBox(height: 12),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 6),
          child: Column(
            children: [
              Card(
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                clipBehavior: Clip.antiAlias,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    ColoredBox(
                      color: AppColors.success,
                      child: Padding(
                        padding: EdgeInsets.all(8),
                        child: Text(
                          "Ngày thi thứ nhất",
                          textAlign: TextAlign.left,
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                      ),
                    ),
                    ColoredBox(
                      color: Colors.white,
                      child: Padding(
                        padding: EdgeInsets.all(8),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text.rich(
                              TextSpan(
                                children: [
                                  TextSpan(
                                    text: "Buổi sáng",
                                    style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontStyle: FontStyle.italic,
                                    ),
                                  ),
                                  TextSpan(text: ": Môn Toán (120 phút)"),
                                ],
                              ),
                            ),
                            Text.rich(
                              TextSpan(
                                children: [
                                  TextSpan(
                                    text: "Buổi chiều",
                                    style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontStyle: FontStyle.italic,
                                    ),
                                  ),
                                  TextSpan(
                                    text:
                                        ": Môn thứ ba (xác định sau) (60 phút)",
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              SizedBox(height: 6),
              Card(
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                clipBehavior: Clip.antiAlias,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    ColoredBox(
                      color: AppColors.success,
                      child: Padding(
                        padding: EdgeInsets.all(8),
                        child: Text(
                          "Ngày thi thứ hai",
                          textAlign: TextAlign.left,
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                      ),
                    ),
                    ColoredBox(
                      color: Colors.white,
                      child: Padding(
                        padding: EdgeInsets.all(8),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text.rich(
                              TextSpan(
                                children: [
                                  TextSpan(
                                    text: "Buổi sáng",
                                    style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontStyle: FontStyle.italic,
                                    ),
                                  ),
                                  TextSpan(text: ": Môn Ngữ văn (120 phút)"),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              SizedBox(height: 6),
              Card(
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                clipBehavior: Clip.antiAlias,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    ColoredBox(
                      color: AppColors.success,
                      child: Padding(
                        padding: EdgeInsets.all(8),
                        child: Text(
                          "Ngày thi thứ ba",
                          textAlign: TextAlign.left,
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                      ),
                    ),
                    ColoredBox(
                      color: Colors.white,
                      child: Padding(
                        padding: EdgeInsets.all(8),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text.rich(
                              TextSpan(
                                children: [
                                  TextSpan(
                                    text: "Buổi sáng",
                                    style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontStyle: FontStyle.italic,
                                    ),
                                  ),
                                  TextSpan(text: ": Các môn chuyên (150 phút)"),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
