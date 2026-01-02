package com.example.lvtn.interceptor;

import com.example.lvtn.annotation.CheckCongBoKetQua;
import com.example.lvtn.service.TrangThaiCSDLService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CongBoKetQuaInterceptor implements HandlerInterceptor {

    private final TrangThaiCSDLService ttcsdlService;

    public CongBoKetQuaInterceptor(TrangThaiCSDLService ttcsdlService) {
        this.ttcsdlService = ttcsdlService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof HandlerMethod handlerMethod) {
            CheckCongBoKetQua annotation =
                    handlerMethod.getMethodAnnotation(CheckCongBoKetQua.class);
            if (annotation != null) {
                kiemTraChoPhepCongBoKetQua(); // Xử lý điều kiện cho phép công bố kết quả
            }
        }
        return true;
    }

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm 'ngày' dd/MM/yyyy");
    private void kiemTraChoPhepCongBoKetQua() {
        LocalDateTime thoiGianCongBo = ttcsdlService.getThoiGianCongBoKetQua();
        Boolean isKhoaCongBo = ttcsdlService.isKhoaCongBoKetQua();
        if (isKhoaCongBo) {
            throw new IllegalStateException("Hệ thống tạm khóa chức năng công bố kết quả, vui lòng thử lại sau!");
        }
        if (LocalDateTime.now().isBefore(thoiGianCongBo)) {
            String formattedTime = thoiGianCongBo.format(FORMATTER);
            throw new IllegalStateException("Chưa đến thời gian công bố kết quả thi! Kết quả dự kiến được công bố lúc " + formattedTime + ".");
        }
    }
}
