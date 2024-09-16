package com.clara.ClaraFuture.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "your-secret-key"; // 실제로는 환경 변수나 설정 파일에서 관리하는 것이 안전합니다.
    private final long ACCESS_TOKEN_EXPIRATION = 3600000; // 1시간 (밀리초)
    private final long REFRESH_TOKEN_EXPIRATION = 604800000; // 7일 (밀리초)

    /**
     * 액세스 토큰 생성
     */
    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     */
    public String createRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * JWT 토큰의 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 리프레시 토큰의 유효성 검증
     */
    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken);
    }

    /**
     * JWT 토큰에서 이메일(사용자 ID) 추출
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * 리프레시 토큰에서 이메일(사용자 ID) 추출
     */
    public String getEmailFromRefreshToken(String refreshToken) {
        return getEmailFromToken(refreshToken);
    }
}
