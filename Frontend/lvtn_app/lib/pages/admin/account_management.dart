import 'dart:async';

import 'package:flutter/material.dart';
import 'package:lvtn_app/api/admin_api.dart';
import 'package:lvtn_app/models/taikhoan.dart';
import 'package:lvtn_app/theme/app_colors.dart';
import 'package:lvtn_app/provider/auth_provider.dart';
import 'package:provider/provider.dart';

class AdminAccountManagement extends StatefulWidget {
  const AdminAccountManagement({super.key});

  @override
  State<AdminAccountManagement> createState() => _AdminAccountManagementState();
}

class _AdminAccountManagementState extends State<AdminAccountManagement> {
  bool isLoading = true;
  List<TaiKhoan> listTK = [];
  List<TaiKhoan> filterList = [];
  final _searchController = TextEditingController();
  Timer? _debounce;

  @override
  void initState() {
    super.initState();
    fetchTaiKhoan();
  }

  Future<void> fetchTaiKhoan() async {
    try {
      final res = await AdminApi.getAccount();
      final data = (res.data['data'] as List)
          .map((e) => TaiKhoan.fromJson(e))
          .toList();
      if (!mounted) return;
      setState(() {
        listTK = data;
        filterList = data;
        isLoading = false;
      });
    } catch (error) {
      debugPrint("Lỗi khi lấy danh sách tài khoản: $error");
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> searchTaiKhoan(String keyword) async {
    try {
      final res = await AdminApi.searchAccount(keyword: keyword);
      final data = (res.data['data'] as List)
          .map((e) => TaiKhoan.fromJson(e))
          .toList();
      setState(() => filterList = data);
    } catch (error) {
      debugPrint("Lỗi khi tìm kiếm: $error");
    }
  }

  void _onSearchChanged(String keyword) {
    if (_debounce?.isActive ?? false) _debounce!.cancel();
    _debounce = Timer(const Duration(milliseconds: 500), () async {
      if (!mounted) return;
      if (keyword.isEmpty) {
        // Trở về danh sách ban đầu
        setState(() => filterList = listTK);
      } else {
        await searchTaiKhoan(keyword);
      }
    });
  }

  Future<void> _onRefresh() async {
    final keyword = _searchController.text.trim();
    if (keyword == "") {
      await fetchTaiKhoan();
    } else {
      await searchTaiKhoan(keyword);
    }
  }

  @override
  Widget build(BuildContext context) {
    final authProvider = Provider.of<AuthProvider>(context, listen: false);
    if (isLoading) {
      return SafeArea(child: const Center(child: CircularProgressIndicator()));
    }
    return SafeArea(
      child: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const SizedBox(height: 8.0),
            Text(
              "Vì lý do an toàn dữ liệu, ứng dụng di động chỉ cho phép xem và lọc danh sách tài khoản. Vui lòng đăng nhập trên máy tính để thực hiện các tác vụ khác.",
              textAlign: TextAlign.center,
              style: TextStyle(fontSize: 18, color: AppColors.danger),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 12.0,
                vertical: 4.0,
              ),
              child: TextField(
                controller: _searchController,
                decoration: InputDecoration(
                  hintText: 'Nhập từ khóa tìm kiếm...',
                  prefixIcon: const Icon(Icons.search),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(12.0),
                  ),
                ),
                onChanged: (value) {
                  _onSearchChanged(value);
                },
              ),
            ),
            if (filterList.isNotEmpty)
              Padding(
                padding: const EdgeInsets.symmetric(
                  horizontal: 8.0,
                  vertical: 8.0,
                ),
                child: Text(
                  "Tìm thấy ${filterList.length} tài khoản:",
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
                        "Không có tài khoản nào được tìm thấy.",
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
                          final tk = filterList[index];
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
                                "Tài khoản: ${tk.tenTK}",
                                style: const TextStyle(
                                  fontWeight: FontWeight.bold,
                                  fontSize: 16,
                                ),
                              ),
                              subtitle: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  if (tk.tenDinhDanh.isNotEmpty)
                                    Text("Tên định danh: ${tk.tenDinhDanh}"),
                                  if (tk.soDinhDanh.isNotEmpty)
                                    Text("Số định danh: ${tk.soDinhDanh}"),
                                  Text(
                                    "Đối tượng: ${authProvider.getLoaiTK(tk.maLoaiTK)}",
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
