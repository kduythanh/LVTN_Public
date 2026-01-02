class PageTitle {
  static const Map<String, String> titles = {
    // Khách (chưa đăng nhập)
    '/': 'TRANG CHỦ',
    '/search-result': 'TRA CỨU KẾT QUẢ THI',
    '/admission-info': 'CHỈ TIÊU TUYỂN SINH',
    '/information': 'VĂN BẢN CÓ LIÊN QUAN',
    '/login': 'ĐĂNG NHẬP',

    // Admin
    '/admin': 'TRANG CHỦ - QUẢN TRỊ VIÊN',
    '/admin/account': 'QUẢN LÝ TÀI KHOẢN',
    '/admin/change-password': "ĐỔI MẬT KHẨU",

    // Học sinh
    '/hocsinh': 'TRANG CHỦ - HỌC SINH',
    '/hocsinh/detail': 'THÔNG TIN HỌC SINH',
    '/hocsinh/change-password': "ĐỔI MẬT KHẨU",

    // SGDĐT
    '/sgddt': 'TRANG CHỦ - HỘI ĐỒNG TUYỂN SINH',
    '/sgddt/thisinh': 'QUẢN LÝ THÍ SINH',
    '/sgddt/trangthai': 'QUẢN LÝ TRẠNG THÁI',
    '/sgddt/thisinh/update-score': 'CẬP NHẬT ĐIỂM THÍ SINH',
    '/sgddt/change-password': "ĐỔI MẬT KHẨU",

    // THCS
    '/thcs': 'TRANG CHỦ - THCS',
    '/thcs/hocsinh': 'QUẢN LÝ HỌC SINH',
    '/thcs/change-password': "ĐỔI MẬT KHẨU",
    '/thcs/hocsinh/detail': 'HỒ SƠ HỌC SINH',

    // THPT
    '/thpt': 'TRANG CHỦ - THPT',
    '/thpt/hocsinh': 'QUẢN LÝ HỌC SINH',
    '/thpt/thisinh': 'QUẢN LÝ THÍ SINH',
    '/thpt/change-password': "ĐỔI MẬT KHẨU",
    '/thpt/hocsinh/detail': 'HỒ SƠ HỌC SINH',

    // Welcome
    '/welcome': '',
  };

  static String of(String path) {
    return titles[path] ?? '';
  }
}
