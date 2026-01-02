package com.example.lvtn.interceptor;

import com.example.lvtn.annotation.CheckCapSoBaoDanh;
import com.example.lvtn.service.TrangThaiCSDLService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CapSoBaoDanhInterceptor implements HandlerInterceptor {

    private final TrangThaiCSDLService ttcsdlService;

    public CapSoBaoDanhInterceptor(TrangThaiCSDLService ttcsdlService) {
        this.ttcsdlService = ttcsdlService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof HandlerMethod handlerMethod) {
            CheckCapSoBaoDanh annotation =
                    handlerMethod.getMethodAnnotation(CheckCapSoBaoDanh.class);
            if (annotation != null) {
                kiemTraChoPhepCapNhat(); // Xử lý điều kiện cập nhật hồ sơ
            }
        }
        return true;
    }

    private void kiemTraChoPhepCapNhat() {
        Boolean isChoPhepCapSoBaoDanh = ttcsdlService.isChoPhepCapSoBaoDanh();
        if (Boolean.FALSE.equals(isChoPhepCapSoBaoDanh)) {
            throw new IllegalStateException("Hệ thống hiện không cho phép cấp số báo danh và phòng thi cho thí sinh! Vui lòng bật chức năng ở mục Quản lý trạng thái để thực hiện chức năng này!");
        }
    }
}
