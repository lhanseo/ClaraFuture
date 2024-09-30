package com.clara.ClaraFuture.controller;

import com.clara.ClaraFuture.service.JwtService;
import com.clara.ClaraFuture.service.ParentService;
import com.clara.ClaraFuture.entity.Parent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
public class OAuth2LoginController {

    private final JwtService jwtService;

    private final ParentService parentService;

    public OAuth2LoginController(JwtService jwtService, ParentService parentService) {
        this.jwtService = jwtService;
        this.parentService = parentService;
    }

    // 리프레시 토큰을 사용한 새로운 액세스 토큰 발급
    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (jwtService.validateRefreshToken(refreshToken)) {
            String email = jwtService.getEmailFromRefreshToken(refreshToken);
            String newAccessToken = jwtService.createAccessToken(email);
            String newRefreshToken = jwtService.createRefreshToken(email);

            // 새로 발급된 JWT와 리프레시 토큰을 ResponseEntity로 반환
            return ResponseEntity.ok().body(new TokenResponse(newAccessToken, newRefreshToken));
        } else {
            // 리프레시 토큰이 유효하지 않거나 만료된 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid or expired refresh token"));
        }
    }

    // 토큰 응답 객체
    static class TokenResponse {
        private String accessToken;
        private String refreshToken;

        public TokenResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    // 리프레시 토큰 요청 객체
    static class RefreshTokenRequest {
        private String refreshToken;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    // 오류 응답 객체
    static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
