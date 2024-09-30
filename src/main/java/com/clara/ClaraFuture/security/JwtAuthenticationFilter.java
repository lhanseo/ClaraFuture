package com.clara.ClaraFuture.security;

import com.clara.ClaraFuture.exception.InvalidJwtTokenException;
import com.clara.ClaraFuture.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        // 토큰이 존재하고 "Bearer "로 시작하는지 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
                if (jwtService.validateToken(token)) {
                    email = jwtService.getEmailFromToken(token);
                }
            } catch (InvalidJwtTokenException e) {
                // 토큰 파싱 중 예외 발생 시 처리 (로그 기록 등)
                logger.error("Invalid JWT token: {}", e);
            }
        }

        // 이메일이 존재하고, 이미 인증 정보가 설정되지 않은 경우
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 사용자 정보를 가져오는 로직 (필요에 따라 구현)
            // 여기서는 간단히 이메일만으로 인증 객체를 생성합니다.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, null);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 인증 컨텍스트에 설정
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }
}
