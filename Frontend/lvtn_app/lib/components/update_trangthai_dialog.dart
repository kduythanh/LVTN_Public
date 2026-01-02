import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lvtn_app/api/sgddt_api.dart';
import 'package:lvtn_app/models/trangthai.dart';
import 'package:lvtn_app/theme/app_colors.dart';

Future<void> showUpdateTrangThaiDialog({
  required BuildContext context,
  required TrangThai selectedTT,
  required VoidCallback onUpdated,
}) async {
  final TextEditingController textController = TextEditingController(
    text: selectedTT.giaTriChuoi ?? "",
  );

  bool isLoading = false;
  bool isError = false;
  String message = '';
  String kieuDuLieu = selectedTT.kieuDuLieu;
  dynamic newVal = selectedTT.kieuDuLieu == "BOOLEAN"
      ? selectedTT.giaTriBoolean
      : selectedTT.kieuDuLieu == "STRING"
      ? selectedTT.giaTriChuoi
      : selectedTT.giaTriTimestamp;

  await showDialog(
    context: context,
    barrierDismissible: false, // không cho tắt khi nhấn ngoài
    builder: (BuildContext context) {
      return StatefulBuilder(
        builder: (context, setState) => AlertDialog(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
          title: Text(
            "Cập nhật trạng thái",
            style: const TextStyle(fontWeight: FontWeight.bold),
          ),
          content: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const SizedBox(height: 10),
                Text(
                  selectedTT.tenTrangThai,
                  style: const TextStyle(fontWeight: FontWeight.w600),
                ),
                const SizedBox(height: 10),

                // BOOLEAN
                if (kieuDuLieu == "BOOLEAN")
                  DropdownButtonFormField<bool>(
                    initialValue: newVal ?? false,
                    items: const [
                      DropdownMenuItem(value: true, child: Text("Cho phép")),
                      DropdownMenuItem(
                        value: false,
                        child: Text("Không cho phép"),
                      ),
                    ],
                    onChanged: (val) => setState(() => newVal = val),
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      labelText: "Giá trị trạng thái",
                    ),
                  ),

                // STRING
                if (kieuDuLieu == "STRING")
                  TextField(
                    controller: textController,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      labelText: "Giá trị trạng thái",
                    ),
                    onChanged: (val) => newVal = val,
                  ),

                // TIMESTAMP
                if (kieuDuLieu == "TIMESTAMP")
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      InkWell(
                        onTap: () async {
                          DateTime initialDateTime;
                          if (newVal is List) {
                            // Trường hợp backend trả về mảng [2025, 10, 17, 8, 32, 25]
                            initialDateTime = DateTime(
                              newVal[0],
                              newVal[1],
                              newVal[2],
                              newVal.length > 3 ? newVal[3] : 0,
                              newVal.length > 4 ? newVal[4] : 0,
                              newVal.length > 5 ? newVal[5] : 0,
                            );
                          } else if (newVal is String && newVal.isNotEmpty) {
                            // Trường hợp backend trả về chuỗi ISO
                            initialDateTime =
                                DateTime.tryParse(newVal) ?? DateTime.now();
                          } else if (newVal is DateTime) {
                            initialDateTime = newVal;
                          } else {
                            initialDateTime = DateTime.now();
                          }
                          final pickedDate = await showDatePicker(
                            context: context,
                            initialDate: initialDateTime,
                            firstDate: DateTime(2020),
                            lastDate: DateTime(2030),
                            confirmText: "Chọn giờ",
                          );
                          if (pickedDate != null) {
                            if (!context.mounted) return;
                            final pickedTime = await showTimePicker(
                              context: context,
                              initialTime: TimeOfDay.fromDateTime(
                                initialDateTime,
                              ),
                              confirmText: "Lưu",
                            );
                            if (pickedTime != null) {
                              final combined = DateTime(
                                pickedDate.year,
                                pickedDate.month,
                                pickedDate.day,
                                pickedTime.hour,
                                pickedTime.minute,
                              );
                              setState(() => newVal = combined);
                            }
                          }
                        },
                        child: InputDecorator(
                          decoration: const InputDecoration(
                            border: OutlineInputBorder(),
                            labelText: "Thời điểm trạng thái",
                          ),
                          child: Text(
                            newVal != null
                                ? DateFormat('dd/MM/yyyy HH:mm').format(
                                    newVal is List
                                        ? DateTime(
                                            newVal[0],
                                            newVal[1],
                                            newVal[2],
                                            newVal[3] ?? 0,
                                            newVal[4] ?? 0,
                                            newVal[5] ?? 0,
                                          )
                                        : DateTime.tryParse(
                                                newVal.toString(),
                                              ) ??
                                              DateTime.now(),
                                  )
                                : "Chưa chọn thời gian",
                          ),
                        ),
                      ),
                      const SizedBox(height: 5),
                      const Text(
                        "Vui lòng bấm vào ô \"Thời điểm trạng thái\", sau đó chọn theo thứ tự: chọn ngày trước, sau đó đến chọn giờ và phút.",
                        style: TextStyle(
                          fontSize: 15,
                          color: Colors.black,
                          fontStyle: FontStyle.italic,
                        ),
                      ),
                    ],
                  ),

                const SizedBox(height: 15),
                if (isLoading) const Center(child: CircularProgressIndicator()),
                if (message.isNotEmpty)
                  Text(
                    message,
                    style: TextStyle(
                      color: isError ? AppColors.danger : AppColors.success,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
              ],
            ),
          ),
          actions: [
            TextButton(
              style: TextButton.styleFrom(backgroundColor: AppColors.secondary),
              onPressed: isLoading ? null : () => Navigator.pop(context),
              child: const Text("Đóng", style: TextStyle(color: Colors.white)),
            ),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColors.success,
              ),
              onPressed: isLoading
                  ? null
                  : () async {
                      setState(() {
                        isLoading = true;
                        message = '';
                        isError = false;
                      });
                      try {
                        dynamic normalizedVal = newVal;
                        if (kieuDuLieu == "TIMESTAMP" && newVal is DateTime) {
                          normalizedVal =
                              "${DateFormat("yyyy-MM-dd'T'HH:mm:ss").format(newVal)}+07:00";
                        }

                        if (kieuDuLieu == "BOOLEAN") {
                          normalizedVal = newVal.toString();
                        }

                        final res = await SGDDTApi.updateTrangThai(
                          maTT: selectedTT.maTT,
                          kieuDuLieu: kieuDuLieu,
                          newVal: normalizedVal,
                        );

                        setState(() {
                          message =
                              res.data["message"] ?? "Cập nhật thành công!";
                          isLoading = false;
                        });

                        // chờ 1 chút rồi đóng
                        await Future.delayed(const Duration(seconds: 1));
                        if (context.mounted) Navigator.pop(context);
                        onUpdated();
                      } catch (e) {
                        setState(() {
                          isError = true;
                          message = "Lỗi khi cập nhật: $e";
                          isLoading = false;
                        });
                      }
                    },
              child: const Text(
                "Cập nhật",
                style: TextStyle(color: Colors.white),
              ),
            ),
          ],
        ),
      );
    },
  );
}
