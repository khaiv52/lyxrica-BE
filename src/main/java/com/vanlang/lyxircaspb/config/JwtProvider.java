package com.vanlang.lyxircaspb.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;

@Service
public class JwtProvider {
    // Tạo khóa bí mật để ký JWT bằng khóa bí mật được định nghĩa trong JwtConstant.SECRET_KEY
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Phương thức để tạo JWT
    public String generateToken(Authentication auth) {
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // Tạo JWT với các thông tin sau:
        String jwt = Jwts.builder()

                // Thời điểm phát hành
                .setIssuedAt(new Date())
                // Thời điểm hết hạn (hiện tại + 846000000 milliseconds ~ 10 ngày)
                .setExpiration(new Date(new Date().getTime() + 846000000))
                // Thêm claim "email" với giá trị là tên của người dùng (auth.getName())
                .claim("email", auth.getName())
                .claim("authorities", authorities)
                // Ký JWT với khóa bí mật
                .signWith(key)
                // Hoàn thiện JWT
                .compact();
        // Trả về JWT dưới dạng chuỗi
        return jwt;
    }

    public String getEmailFromToken(String jwt) {
        // Loại bỏ tiền tố "Bearer " từ chuỗi JWT
        jwt = jwt.substring(7);

        // Giải mã JWT để lấy các claims
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        // Lấy email từ claims
        String email = String.valueOf(claims.get("email"));


        return email;
    }
}
