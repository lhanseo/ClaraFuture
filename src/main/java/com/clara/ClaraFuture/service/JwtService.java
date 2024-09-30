package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.exception.InvalidJwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long ACCESS_TOKEN_EXPIRATION = 3600000; // 1시간 (밀리초)
    private final long REFRESH_TOKEN_EXPIRATION = 604800000; // 7일 (밀리초)

    public JwtService(@Value("${jwt.secret}") String secretKeyString) {
        if (secretKeyString.length() < 32) { // HS256 기준 최소 256비트 (32바이트)
            throw new IllegalArgumentException("JWT 비밀 키는 최소 256비트(32바이트) 이상이어야 합니다.");
        }
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 액세스 토큰 생성
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     */
    public String createRefreshToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 토큰에서 클레임 추출
     */
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new InvalidJwtTokenException("토큰이 유효하지 않습니다.", e);
        }
    }

    /**
     * 토큰의 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (InvalidJwtTokenException e) {
            // 로그 기록을 원하시면 여기에 추가
            return false;
        }
    }

    /**
     * JWT 토큰에서 이메일(사용자 ID) 추출
     */
    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 리프레시 토큰의 유효성 검증
     */
    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken);
    }

    /**
     * 리프레시 토큰에서 이메일(사용자 ID) 추출
     */
    public String getEmailFromRefreshToken(String refreshToken) {
        return getEmailFromToken(refreshToken);
    }
}
