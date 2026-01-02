package com.example.lvtn.common;

import com.example.lvtn.model.TaiKhoan;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "LuanVanTotNghiep-HocKy1-NamHoc2025-2026"; // nên để trong config/ENV
//    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 giờ

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(TaiKhoan tk) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", tk.getMaLoaiTK());  // thêm quyền vào claim
        claims.put("soDinhDanh", tk.getSoDinhDanh());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(tk.getTenTK())
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public int extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Object value = claims.get("role");
        if (value instanceof Integer i) return i;
        if (value instanceof Long l) return l.intValue();
        if (value instanceof String s) return Integer.parseInt(s);
        return -1;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

