
# HỆ THỐNG HỖ TRỢ TUYỂN SINH LỚP 10 THÀNH PHỐ CẦN THƠ *(Grade 10 Admission Management System for Can Tho City)*

*Đề tài Luận văn Tốt nghiệp ngành Công nghệ Thông tin (CLC), đã bảo vệ ngày **19/12/2025***

Sinh viên thực hiện: **Kim Duy Thành (B2105686)**

## THIẾT KẾ HỆ THỐNG (ARCHITECTURE - TECH STACK)

- Mô hình thiết kế: **Client - Server**
- Backend: **Java Spring Boot**, Cơ sở dữ liệu **MySQL**
- Frontend:
	- Ứng dụng web (Web application): **React.js** framework, **HTML**, **CSS**, **Bootstrap**
	- Ứng dụng di động (Mobile application): **Dart** và **Flutter**
- API hỗ trợ: **reCAPTCHA** và **Google Maps API**

## CÁC NHÓM NGƯỜI DÙNG
- Người dùng khách (guest)
- Quản trị viên hệ thống
- Cán bộ Hội đồng Tuyển sinh (Sở GD&ĐT)
- Cán bộ phụ trách tại trường THCS
- Cán bộ phụ trách tại trường THPT
- Học sinh

## CÁC CHỨC NĂNG CHÍNH (phân theo nhóm người dùng)
- **Nhóm người dùng khách (không cần đăng nhập):**
	+ Xem thông tin về kỳ thi
	+ Xem chỉ tiêu tuyển sinh và số lượng hồ sơ đã đăng ký (phân loại theo THPT thường và lớp chuyên nằm trong THPT chuyên)
	+ Xem văn bản có liên quan đến kỳ thi
	+ Tra cứu điểm thi (có thông tin về nguyện vọng đậu)

- **Nhóm người dùng Quản trị viên (đăng nhập bằng tài khoản quản trị viên):**
	+ Xem danh sách tài khoản cán bộ, học sinh đã tạo
	+ Xuất danh sách tài khoản đã tạo ra Excel
	+ Tìm kiếm, lọc tên tài khoản cán bộ, học sinh
	+ Thêm tài khoản cán bộ (thủ công hoặc import từ file Excel)
	+ Xóa tài khoản cán bộ
	+ Đặt lại mật khẩu cho tài khoản người dùng
	+ Đổi mật khẩu admin

- **Nhóm người dùng Cán bộ Hội đồng tuyển sinh (Sở GD&ĐT) (đăng nhập bằng tài khoản của cán bộ sở GD&ĐT):**
	+ Khóa/mở chức năng xem điểm, chức năng nhập hồ sơ mới
	+ Xem danh sách thí sinh của các trường
	+ Cập nhật danh sách thí sinh của các hội đồng thi, cấp số báo danh và phòng thi cho thí sinh các trường
	+ Tìm kiếm thí sinh bằng SBD
	+ Cập nhật điểm (thủ công hoặc import từ file Excel)
	+ Chạy hệ thống xét tuyển (lọc ảo) và sinh kết quả trúng tuyển
	+ Xuất bảng điểm qua Excel
	+ Xuất kết quả thi toàn hội đồng thi của sở GD&ĐT
	+ Đổi mật khẩu

- **Nhóm người dùng Cán bộ tại trường THCS (đăng nhập bằng tài khoản của cán bộ THCS):**
	+ Hiển thị thông tin tổng quan hồ sơ học sinh của trường
	+ Xuất danh sách học sinh ra Excel
	+ Tìm kiếm, lọc hồ sơ học sinh
	+ Xem chi tiết hồ sơ của học sinh
	+ Thêm hồ sơ mới (thủ công)
	+ Sửa hồ sơ (thủ công)
	+ Import dữ liệu từ file Excel để thêm/cập nhật hồ sơ học sinh, tích hợp tạo và cấp tài khoản cho học sinh
	+ Xóa hồ sơ (thủ công hoặc xóa toàn bộ hồ sơ của trường đó)
	+ Đổi mật khẩu

- **Nhóm người dùng Cán bộ tại trường THPT (đăng nhập bằng tài khoản của cán bộ THPT):**

	+ Hiển thị thông tin tổng quan hồ sơ thí sinh đăng ký
	+ Tìm kiếm, lọc hồ sơ thí sinh
	+ Xem chi tiết hồ sơ của thí sinh
	+ Thêm hồ sơ mới (thủ công)
	+ Sửa hồ sơ (thủ công)
	+ Import dữ liệu từ file Excel để thêm/cập nhật hồ sơ học sinh, tích hợp tạo và cấp tài khoản cho học sinh
	+ Xóa hồ sơ (thủ công hoặc xóa toàn bộ hồ sơ có liên quan đến trường đó)
	+ Xuất danh sách thí sinh, phòng thi qua Excel
	+ Xuất kết quả thi của thí sinh dự thi tại trường THPT
	+ Đổi mật khẩu

- **Nhóm người dùng Học sinh (đăng nhập bằng tài khoản học sinh):**
	+ Hiển thị thông tin chi tiết hồ sơ đăng ký
	+ Cập nhật thông tin cá nhân
	+ Xuất phiếu đăng ký dự thi
	+ Xem kết quả thi cá nhân
	+ Đổi mật khẩu

## HƯỚNG DẪN CÀI ĐẶT HỆ THỐNG

### Điều kiện cần

- Đã có tài khoản Google Cloud và có khóa reCAPTCHA (để đăng ký reCAPTCHA phục vụ quy trình xử lý đăng nhập và xem kết quả thi)
- Cài đặt Visual Studio Code, IntelliJ IDEA, Android Studio làm trình biên dịch và soạn thảo mã nguồn
- Cài đặt sẵn Node.js để hỗ trợ biên dịch ứng dụng web
- Cài đặt sẵn extension Dart và Flutter để hỗ trợ biên dịch ứng dụng di động
- Có máy sử dụng hệ điều hành Android (máy thật hoặc máy ảo (emulator))
---
### Cách cài đặt

1. Clone mã nguồn này về máy tính và lưu vào 1 folder thích hợp.
2. Kích hoạt reCAPTCHA bằng cách copy khóa công khai (public key) và khóa bí mật (private key) vào các file sau ở phía frontend và backend để kích hoạt:
    - File ```application.properties``` (trong ```Backend```): Tìm kiếm mục ```google.recaptcha.secret=<private key bạn đã tạo>```
    - Tạo file ```.env``` (trong ```Frontend/lvtn-web```) với thông tin: ```VITE_RECAPTCHA_SITE_KEY=<public key bạn đã tạo>```
    - Tạo file ```.env.dev``` và ```.env.prod``` (trong ```Frontend/lvtn_app/assets```) với thông tin: ```API_BASE_URL=http://10.0.2.2:8080/api```
    - Trong file ```recaptcha.html``` (trong ```Frontend/lvtn_app/assets```): Cập nhật lại ```sitekey``` với public key bạn đã tạo
4. Tạo Cơ sở dữ liệu với các thông tin bảng được cho trong báo cáo (bản PDF).
5. Cấu hình hệ thống:
---
### Backend
- Di chuyển vào thư mục backend:
```
cd Backend
```
- Cấu hình file application.properties hoặc .env (DB_URL, username, password)
- Build và chạy ứng dụng:
```
./mvnw spring-boot:run
```
---
### Frontend (web)
- Di chuyển vào thư mục frontend:
```
cd Frontend/lvtn-web
```
- Cài đặt thư viện:
```
npm install
```
- Chạy ứng dụng ở chế độ development:
```
npm start
```
---
### Mobile app
- Di chuyển vào thư mục mobile:
```
cd Frontend/lvtn_app
```
- Lấy các package cần thiết:
```
flutter pub get
```
- Kiểm tra thiết bị kết nối:
```
flutter devices
```
- Chạy app:
```
flutter run
```

## HƯỚNG DẪN SỬ DỤNG CHI TIẾT

- Xem báo cáo (bằng tiếng Anh) tại file báo cáo trong mục Bao_cao: [File báo cáo](https://github.com/kduythanh/LVTN/blob/master/Bao_cao/ThesisReport_B2105686.pdf).
- Bản tiếng Việt sẽ được cập nhật sau!

