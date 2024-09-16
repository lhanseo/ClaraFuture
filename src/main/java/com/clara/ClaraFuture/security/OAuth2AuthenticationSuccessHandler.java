package com.clara.ClaraFuture.security;

import com.clara.ClaraFuture.util.JwtUtil;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String email = authentication.getName(); // 소셜 로그인한 사용자의 이메일 추출
        String token = jwtUtil.generateToken(email); // JWT 토큰 발급

        // 발급한 JWT 토큰을 응답으로 클라이언트에게 전달
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName(); // 소셜 로그인한 사용자의 이메일 추출
        String token = jwtUtil.generateToken(email); // JWT 토큰 발급

        // 발급한 JWT 토큰을 응답으로 클라이언트에게 전달
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
