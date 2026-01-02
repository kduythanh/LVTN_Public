package com.example.lvtn.interceptor;

import com.example.lvtn.annotation.CheckCapNhatDiem;
import com.example.lvtn.annotation.CheckXetTuyen;
import com.example.lvtn.service.TrangThaiCSDLService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class XetTuyenInterceptor implements HandlerInterceptor {

    private final TrangThaiCSDLService ttcsdlService;

    public XetTuyenInterceptor(TrangThaiCSDLService ttcsdlService) {
        this.ttcsdlService = ttcsdlService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof HandlerMethod handlerMethod) {
            CheckXetTuyen annotation =
                    handlerMethod.getMethodAnnotation(CheckXetTuyen.class);
            if (annotation != null) {
                kiemTraChoPhepXetTuyen(); // Xử lý điều kiện cập nhật
            }
        }
        return true;
    }

    private void kiemTraChoPhepXetTuyen() {
        Boolean isChoPhepXetTuyen = ttcsdlService.isChoPhepXetTuyen();
        if (Boolean.FALSE.equals(isChoPhepXetTuyen)) {
            throw new IllegalStateException("Hệ thống hiện không cho phép chạy hệ thống xét tuyển! Vui lòng bật chức năng ở mục Quản lý trạng thái để thực hiện chức năng này!");
        }
    }
}
