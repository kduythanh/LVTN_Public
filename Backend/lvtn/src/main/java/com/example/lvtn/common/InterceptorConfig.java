package com.example.lvtn.common;

import com.example.lvtn.interceptor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CapNhatHoSoInterceptor capNhatHoSoInterceptor;
    private final CapSoBaoDanhInterceptor capSoBaoDanhInterceptor;
    private final CapNhatDiemInterceptor capNhatDiemInterceptor;
    private final XetTuyenInterceptor xetTuyenInterceptor;
    private final CongBoKetQuaInterceptor congBoKetQuaInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(capNhatHoSoInterceptor)
                .addPathPatterns("/api/hocsinh/**", "/api/thcs/**", "/api/thpt/**");
        registry.addInterceptor(capSoBaoDanhInterceptor)
                .addPathPatterns("/api/sgddt/thisinh/capnhat");
        registry.addInterceptor(capNhatDiemInterceptor)
                .addPathPatterns("/api/sgddt/thisinh/kqthi/**");
        registry.addInterceptor(xetTuyenInterceptor)
                .addPathPatterns("/api/sgddt/xettuyen");
        registry.addInterceptor(congBoKetQuaInterceptor)
                .addPathPatterns("/api/common/ketquathi/**", "/api/hocsinh/ketquathi/**");

    }
}
