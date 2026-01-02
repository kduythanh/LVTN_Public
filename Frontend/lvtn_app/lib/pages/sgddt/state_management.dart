import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lvtn_app/api/sgddt_api.dart';
import 'package:lvtn_app/components/update_trangthai_dialog.dart';
import 'package:lvtn_app/models/trangthai.dart';
import 'package:lvtn_app/theme/app_colors.dart';

class HDTSStateManagement extends StatefulWidget {
  const HDTSStateManagement({super.key});

  @override
  State<HDTSStateManagement> createState() => _HDTSStateManagementState();
}

class _HDTSStateManagementState extends State<HDTSStateManagement> {
  bool isLoading = true;
  List<TrangThai> listTT = [];

  String formatTimestamp(DateTime? date) {
    if (date == null) return '';
    return DateFormat('dd/MM/yyyy HH:mm:ss').format(date.toLocal());
  }

  @override
  void initState() {
    super.initState();
    fetchTrangThai();
  }

  Future<void> fetchTrangThai() async {
    try {
      final res = await SGDDTApi.getTrangThai();
      final data = (res.data['data'] as List)
          .map((e) => TrangThai.fromJson(e))
          .toList();
      if (!mounted) return;
      setState(() {
        listTT = data;
        isLoading = false;
      });
    } catch (error) {
      debugPrint("Lỗi khi lấy danh sách trạng thái: $error");
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> _onRefresh() async {
    await fetchTrangThai();
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
            if (listTT.isNotEmpty)
              Padding(
                padding: const EdgeInsets.symmetric(
                  horizontal: 8.0,
                  vertical: 8.0,
                ),
                child: Text(
                  "Tìm thấy ${listTT.length} trạng thái:",
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            Expanded(
              child: listTT.isEmpty
                  ? const Center(
                      child: Text(
                        "Không có trạng thái nào được tìm thấy.",
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
                        itemCount: listTT.length,
                        itemBuilder: (context, index) {
                          final tt = listTT[index];
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
                                "${tt.maTT}: ${tt.tenTrangThai}",
                                style: const TextStyle(
                                  fontWeight: FontWeight.bold,
                                  fontSize: 16,
                                ),
                              ),
                              subtitle: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  if (tt.kieuDuLieu == "BOOLEAN")
                                    if (tt.giaTriBoolean == true)
                                      Text(
                                        "Giá trị trạng thái: Cho phép",
                                        style: TextStyle(
                                          color: AppColors.success,
                                          fontWeight: FontWeight.bold,
                                        ),
                                      )
                                    else
                                      Text(
                                        "Giá trị trạng thái: Không cho phép",
                                        style: TextStyle(
                                          color: AppColors.danger,
                                          fontWeight: FontWeight.bold,
                                        ),
                                      )
                                  else if (tt.kieuDuLieu == "TIMESTAMP")
                                    Text(
                                      "Giá trị trạng thái: ${formatTimestamp(tt.giaTriTimestamp)}",
                                      style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                      ),
                                    )
                                  else if (tt.kieuDuLieu == "STRING")
                                    Text(
                                      "Giá trị trạng thái: ${tt.giaTriChuoi}",
                                      style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                  if (tt.tgCapNhat != null)
                                    Text(
                                      "Cập nhật lần cuối: ${formatTimestamp(tt.tgCapNhat)}",
                                    ),
                                  const SizedBox(height: 12),
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.end,
                                    children: [
                                      ElevatedButton.icon(
                                        style: ElevatedButton.styleFrom(
                                          backgroundColor: AppColors.warning,
                                          shape: RoundedRectangleBorder(
                                            borderRadius: BorderRadius.circular(
                                              8,
                                            ),
                                          ),
                                        ),
                                        onPressed: () async {
                                          await showUpdateTrangThaiDialog(
                                            context: context,
                                            selectedTT: tt,
                                            onUpdated: fetchTrangThai,
                                          );
                                        },
                                        icon: const Icon(
                                          Icons.arrow_forward,
                                          color: Colors.black,
                                        ),
                                        label: const Text(
                                          "Cập nhật trạng thái",
                                          style: TextStyle(color: Colors.black),
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
