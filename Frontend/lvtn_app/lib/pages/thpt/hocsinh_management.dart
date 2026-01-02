import 'dart:async';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:lvtn_app/api/thpt_api.dart';
import 'package:lvtn_app/models/hocsinh_fulldata.dart';
import 'package:lvtn_app/provider/auth_provider.dart';
import 'package:lvtn_app/theme/app_colors.dart';
import 'package:provider/provider.dart';

class THPTHocSinhManagement extends StatefulWidget {
  const THPTHocSinhManagement({super.key});

  @override
  State<THPTHocSinhManagement> createState() => _THPTHocSinhManagementState();
}

class _THPTHocSinhManagementState extends State<THPTHocSinhManagement> {
  bool isLoading = true;
  List<HocSinhFullData> listHS = [];
  List<HocSinhFullData> filterList = [];
  final _searchController = TextEditingController();
  Timer? _debounce;

  @override
  void initState() {
    super.initState();
    fetchHocSinh();
  }

  Future<void> fetchHocSinh() async {
    try {
      final authProvider = Provider.of<AuthProvider>(context, listen: false);
      final user = authProvider.user ?? {};
      final res = await THPTApi.getListHocSinh(maTHPT: user['soDinhDanh']);
      final data = (res.data['data'] as List)
          .map((e) => HocSinhFullData.fromJson(e))
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

  Future<void> searchHocSinh(String keyword) async {
    try {
      final authProvider = Provider.of<AuthProvider>(context, listen: false);
      final user = authProvider.user ?? {};
      final res = await THPTApi.searchHocSinh(
        maTHPT: user['soDinhDanh'],
        keyword: keyword,
      );
      final data = (res.data['data'] as List)
          .map((e) => HocSinhFullData.fromJson(e))
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
        setState(() => filterList = listHS);
      } else {
        await searchHocSinh(keyword);
      }
    });
  }

  Future<void> _onRefresh() async {
    final keyword = _searchController.text.trim();
    if (keyword == "") {
      await fetchHocSinh();
    } else {
      await searchHocSinh(keyword);
    }
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
            Text(
              "Vì lý do an toàn dữ liệu, ứng dụng di động chỉ cho phép xem và lọc danh sách học sinh. Vui lòng đăng nhập trên máy tính để thực hiện các tác vụ khác.",
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
                  "Tìm thấy ${filterList.length} học sinh:",
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
                        "Không có học sinh nào được tìm thấy.",
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
                          final hs = filterList[index];
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
                                "${hs.thongTinHS.maHS} - ${hs.thongTinHS.hoVaChuLotHS} ${hs.thongTinHS.tenHS}",
                                style: const TextStyle(
                                  fontWeight: FontWeight.bold,
                                  fontSize: 16,
                                ),
                              ),
                              subtitle: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    hs.thongTinHS.gioiTinh == true
                                        ? "Giới tính: Nữ"
                                        : "Giới tính: Nam",
                                  ),
                                  if (hs.thongTinHS.ngaySinh != null)
                                    Text(
                                      "Ngày sinh: ${hs.thongTinHS.ngaySinh}",
                                    ),
                                  if (hs.thongTinHS.noiSinh != null)
                                    Text("Nơi sinh: ${hs.thongTinHS.noiSinh}"),
                                  if (hs.thongTinHS.tenTHCSNgoaiTPCT != null &&
                                      hs.thongTinHS.tenXaNgoaiTPCT != null &&
                                      hs.thongTinHS.tenTinhNgoaiTPCT != null)
                                    Text(
                                      "Trường: ${hs.thongTinHS.tenTHCSNgoaiTPCT} (${hs.thongTinHS.tenXaNgoaiTPCT}, ${hs.thongTinHS.tenTinhNgoaiTPCT})",
                                    ),

                                  if (hs.nguyenVong.isNotEmpty) ...[
                                    Text(
                                      hs.nguyenVong.first.maLopChuyen != null
                                          ? "NV cao nhất: NV${hs.nguyenVong.first.thuTu} - ${hs.nguyenVong.first.tenTHPT} (${hs.nguyenVong.first.tenLopChuyen})"
                                          : "NV cao nhất: NV${hs.nguyenVong.first.thuTu} - ${hs.nguyenVong.first.tenTHPT}",
                                    ),
                                    const SizedBox(height: 12),
                                    Row(
                                      mainAxisAlignment: MainAxisAlignment.end,
                                      children: [
                                        ElevatedButton.icon(
                                          style: ElevatedButton.styleFrom(
                                            backgroundColor: AppColors.success,
                                            shape: RoundedRectangleBorder(
                                              borderRadius:
                                                  BorderRadius.circular(8),
                                            ),
                                          ),
                                          onPressed: () => context.push(
                                            '/thpt/hocsinh/detail/${hs.thongTinHS.maHS}',
                                          ),
                                          icon: const Icon(
                                            Icons.arrow_forward,
                                            color: Colors.white,
                                          ),
                                          label: const Text(
                                            "Xem chi tiết",
                                            style: TextStyle(
                                              color: Colors.white,
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ],
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
