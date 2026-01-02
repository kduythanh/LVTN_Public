package com.example.lvtn.common;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
                // REST API → tắt CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Bật CORS cho FE
                .cors(Customizer.withDefaults())
                // Không dùng session phía server (JWT stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Phân quyền theo đường dẫn
                .authorizeHttpRequests(auth -> auth
                                // Public (guest): cho phép truy cập không cần token
                                // !!! Sẽ chỉnh sửa lại sau khi đã xác định rõ các API cần thiết !!!
                                .requestMatchers(
                                        "/**",
                                        "/api/common/login",
                                        "/api/common/logout",
                                        "/api/common/trangthai/**",
                                        "/api/common/anh/**",
                                        "/api/common/thpt/**",
                                        "/api/common/ketquathi/**",
                                        "/error",
                                        "/v3/api-docs/**",     // Swagger
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/templates/**"
                                ).permitAll()

                                // Ví dụ các API yêu cầu quyền cụ thể:
                                .requestMatchers("/api/common/**").hasAnyRole("ADMIN", "SGDDT", "THCS", "THPT", "HOCSINH")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/sgddt/**").hasRole("SGDDT")
                                .requestMatchers("/api/thcs/**").hasRole("THCS")
                                .requestMatchers("/api/thpt/**").hasRole("THPT")
                                .requestMatchers("/api/hocsinh/**").hasRole("HOCSINH")

                                // Mặc định: các API còn lại yêu cầu đăng nhập
                                .anyRequest().authenticated()
                )
                // Trả JSON gọn cho 401/403 (tuỳ chọn)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json;charset=UTF-8");
                            res.getWriter().write("""
                                        {"status":401,"message":"Vui lòng đăng nhập để truy cập chức năng này!","data":null}
                                    """);
                        })
                        .accessDeniedHandler((req, res, e) -> {
                            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            res.setContentType("application/json;charset=UTF-8");
                            res.getWriter().write("""
                                        {"status":403,"message":"Bạn không được phép truy cập chức năng này!","data":null}
                                    """);
                        })
                )
                // Đăng ký JWT filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Hàm hash mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cho phép FE gọi API (chỉnh domain/port cho đúng)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "https://unignominious-lorena-isologous.ngrok-free.dev/",
                "http://26.212.120.224:5173",
                "http://26.212.120.224")); // sửa theo FE của bạn
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type", "X-Captcha-Token"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
