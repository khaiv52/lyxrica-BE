package com.vanlang.lyxrica_springb.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtValidator extends OncePerRequestFilter {

    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy JWT từ header của yêu cầu
        String jwt = request.getHeader("Authorization");

        if (jwt == null || !jwt.startsWith("Bearer ")) {
            // Nếu không có JWT hoặc không có tiền tố "Bearer ", không thực hiện tiếp và chuyển sang bộ lọc tiếp theo
            filterChain.doFilter(request, response);
            return;
        }

        // Xóa bỏ tiền tố "Bearer " từ chuỗi JWT
        jwt = jwt.substring(7);

        try {
            // Giải mã JWT để lấy các claims
            Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

            // Lấy email từ claims
            String email = String.valueOf(claims.get("email"));

            // Lấy danh sách các authorities từ claims
            String authorities = String.valueOf(claims.get("authorities"));

            // Chuyển đổi chuỗi authorities thành danh sách các GrantedAuthority
            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            // Tạo đối tượng Authentication từ email và authorities
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

            // Đặt Authentication vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // Nếu có lỗi trong quá trình giải mã JWT, ném ngoại lệ BadCredentialsException
            throw new BadCredentialsException("Invalid token", e);
        }

        // Chuyển sang bộ lọc tiếp theo trong chuỗi
        filterChain.doFilter(request, response);
    }

}
