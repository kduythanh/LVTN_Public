import 'dart:async';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:lvtn_app/api/sgddt_api.dart';
import 'package:lvtn_app/models/thisinh_fulldata.dart';
import 'package:lvtn_app/theme/app_colors.dart';

class HDTSThiSinhManagement extends StatefulWidget {
  const HDTSThiSinhManagement({super.key});

  @override
  State<HDTSThiSinhManagement> createState() => _HDTSThiSinhManagementState();
}

class _HDTSThiSinhManagementState extends State<HDTSThiSinhManagement> {
  bool isLoading = true;
  List<ThiSinhFullData> listHS = [];
  List<ThiSinhFullData> filterList = [];
  final _searchController = TextEditingController();
  // Timer? _debounce;

  @override
  void initState() {
    super.initState();
    fetchThiSinh();
  }

  Future<void> fetchThiSinh() async {
    try {
      final res = await SGDDTApi.getListThiSinh();
      final data = (res.data['data'] as List)
          .map((e) => ThiSinhFullData.fromJson(e))
          .toList();
      if (!mounted) return;
      setState(() {
        listHS = data;
        filterList = data;
        isLoading = false;
      });
    } catch (error) {
      debugPrint("Lỗi khi lấy danh sách học sinh: $error");
      setState(() {
        isLoading = false;
      });
    }
  }

  // Future<void> searchThiSinh(String keyword) async {
  //   try {
  //     final authProvider = Provider.of<AuthProvider>(context, listen: false);
  //     final user = authProvider.user ?? {};
  //     final res = await THPTApi.searchThiSinh(
  //       maTHPT: user['soDinhDanh'],
  //       keyword: keyword,
  //     );
  //     final data = (res.data['data'] as List)
  //         .map((e) => ThiSinhFullData.fromJson(e))
  //         .toList();
  //     setState(() => filterList = data);
  //   } catch (error) {
  //     debugPrint("Lỗi khi tìm kiếm: $error");
  //   }
  // }

  // void _onSearchChanged(String keyword) {
  //   if (_debounce?.isActive ?? false) _debounce!.cancel();
  //   _debounce = Timer(const Duration(milliseconds: 500), () async {
  //     if (!mounted) return;
  //     if (keyword.isEmpty) {
  //       // Trở về danh sách ban đầu
  //       setState(() => filterList = listHS);
  //     } else {
  //       await searchThiSinh(keyword);
  //     }
  //   });
  // }

  Future<void> _onRefresh() async {
    final keyword = _searchController.text.trim();
    if (keyword == "") await fetchThiSinh();
    // } else {
    //   await searchThiSinh(keyword);
    // }
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return SafeArea(child: const Center(child: CircularProgressIndicator()));
    }
    return SafeArea(
      child: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const SizedBox(height: 8.0),
            // Padding(
            //   padding: const EdgeInsets.symmetric(
            //     horizontal: 12.0,
            //     vertical: 4.0,
            //   ),
            //   child: TextField(
            //     controller: _searchController,
            //     decoration: InputDecoration(
            //       hintText: 'Nhập từ khóa tìm kiếm...',
            //       prefixIcon: const Icon(Icons.search),
            //       border: OutlineInputBorder(
            //         borderRadius: BorderRadius.circular(12.0),
            //       ),
            //     ),
            //     onChanged: (value) {
            //       _onSearchChanged(value);
            //     },
            //   ),
            // ),
            if (filterList.isNotEmpty)
              Padding(
                padding: const EdgeInsets.symmetric(
                  horizontal: 8.0,
                  vertical: 8.0,
                ),
                child: Text(
                  "Tìm thấy ${filterList.length} thí sinh:",
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            Expanded(
              child: filterList.isEmpty
                  ? const Center(
                      child: Text(
                        "Không có thí sinh nào được tìm thấy.",
                        style: TextStyle(
                          fontSize: 16,
                          color: AppColors.danger,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    )
                  : RefreshIndicator(
                      onRefresh: () => _onRefresh(),
                      child: ListView.builder(
                        itemCount: filterList.length,
                        itemBuilder: (context, index) {
                          final ts = filterList[index];
                          return Card(
                            margin: const EdgeInsets.symmetric(
                              horizontal: 12,
                              vertical: 6,
                            ),
                            elevation: 3,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                            surfaceTintColor: Colors.greenAccent,
                            child: ListTile(
                              title: Text(
                                "${ts.maHS} - ${ts.hoVaChuLotHS} ${ts.tenHS}",
                                style: const TextStyle(
                                  fontWeight: FontWeight.bold,
                                  fontSize: 16,
                                ),
                              ),
                              subtitle: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  if (ts.soBaoDanh != null)
                                    Text("Số báo danh: ${ts.soBaoDanh}"),
                                  if (ts.diemToan != null)
                                    Text(
                                      "Điểm Toán: ${ts.diemToan!.toStringAsFixed(2)}",
                                    ),
                                  if (ts.diemVan != null)
                                    Text(
                                      "Điểm Ngữ văn: ${ts.diemVan!.toStringAsFixed(2)}",
                                    ),
                                  if (ts.diemMonThu3 != null)
                                    Text(
                                      "Điểm môn thứ 3: ${ts.diemMonThu3!.toStringAsFixed(2)}",
                                    ),
                                  if (ts.diemMonChuyen != null)
                                    Text(
                                      "Điểm môn chuyên: ${ts.diemMonChuyen!.toStringAsFixed(2)}",
                                    ),
                                  if (ts.soBaoDanh != null)
                                    const SizedBox(height: 12),
                                  if (ts.soBaoDanh != null)
                                    Row(
                                      mainAxisAlignment: MainAxisAlignment.end,
                                      children: [
                                        ElevatedButton.icon(
                                          style: ElevatedButton.styleFrom(
                                            backgroundColor: AppColors.warning,
                                            shape: RoundedRectangleBorder(
                                              borderRadius:
                                                  BorderRadius.circular(8),
                                            ),
                                          ),
                                          onPressed: () async {
                                            final result = await context.push(
                                              '/sgddt/thisinh/update-score/${ts.soBaoDanh}',
                                            );
                                            if (result == true) {
                                              await fetchThiSinh();
                                            }
                                          },
                                          icon: const Icon(
                                            Icons.arrow_forward,
                                            color: Colors.black,
                                          ),
                                          label: const Text(
                                            "Cập nhật điểm",
                                            style: TextStyle(
                                              color: Colors.black,
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                ],
                              ),
                            ),
                          );
                        },
                      ),
                    ),
            ),
          ],
        ),
      ),
    );
  }
}
