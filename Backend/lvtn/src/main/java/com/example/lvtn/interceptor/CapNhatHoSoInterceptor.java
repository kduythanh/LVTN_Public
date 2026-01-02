package com.example.lvtn.interceptor;

import com.example.lvtn.annotation.CheckCapNhatHoSo;
import com.example.lvtn.service.TrangThaiCSDLService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class CapNhatHoSoInterceptor implements HandlerInterceptor {

    private final TrangThaiCSDLService ttcsdlService;

    public CapNhatHoSoInterceptor(TrangThaiCSDLService ttcsdlService) {
        this.ttcsdlService = ttcsdlService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof HandlerMethod handlerMethod) {
            CheckCapNhatHoSo annotation =
                    handlerMethod.getMethodAnnotation(CheckCapNhatHoSo.class);
            if (annotation != null) {
                kiemTraChoPhepCapNhat(); // Xử lý điều kiện cập nhật hồ sơ
            }
        }
        return true;
    }

    private void kiemTraChoPhepCapNhat() {
        // Logic nghiệp vụ kiểm tra trạng thái kỳ thi
        LocalDateTime thoiHanDangKy = ttcsdlService.getThoiHanDangKy();
        Boolean isChoPhepCapNhat = ttcsdlService.isChoPhepCapNhatHoSo();

        // Kiểm tra thời hạn đăng ký
        if (thoiHanDangKy != null && LocalDateTime.now().isAfter(thoiHanDangKy)) {
            if (Boolean.TRUE.equals(isChoPhepCapNhat)) {
                ttcsdlService.updateTrangThai(1, "BOOLEAN", "false");
            }
            throw new IllegalStateException("Đã hết thời hạn đăng ký hồ sơ theo quy định!");
        }

        // Kiểm tra trạng thái cho phép cập nhật
        if (!Boolean.TRUE.equals(isChoPhepCapNhat)) {
            throw new IllegalStateException("Hệ thống hiện không cho phép thêm/cập nhật/xóa hồ sơ!");
        }
    }
}
