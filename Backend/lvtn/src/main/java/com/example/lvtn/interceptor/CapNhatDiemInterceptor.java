package com.example.lvtn.interceptor;

import com.example.lvtn.annotation.CheckCapNhatDiem;
import com.example.lvtn.annotation.CheckCapSoBaoDanh;
import com.example.lvtn.service.TrangThaiCSDLService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CapNhatDiemInterceptor implements HandlerInterceptor {

    private final TrangThaiCSDLService ttcsdlService;

    public CapNhatDiemInterceptor(TrangThaiCSDLService ttcsdlService) {
        this.ttcsdlService = ttcsdlService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof HandlerMethod handlerMethod) {
            CheckCapNhatDiem annotation =
                    handlerMethod.getMethodAnnotation(CheckCapNhatDiem.class);
            if (annotation != null) {
                kiemTraChoPhepCapNhat(); // Xử lý điều kiện cập nhật
            }
        }
        return true;
    }

    private void kiemTraChoPhepCapNhat() {
        Boolean isChoPhepCapNhatDiem = ttcsdlService.isChoPhepCapNhatDiem();
        if (Boolean.FALSE.equals(isChoPhepCapNhatDiem)) {
            throw new IllegalStateException("Hệ thống hiện không cho phép cập nhật điểm thí sinh! Vui lòng bật chức năng ở mục Quản lý trạng thái để thực hiện chức năng này!");
        }
    }
}
