package com.clara.ClaraFuture.controller;

import com.clara.ClaraFuture.service.JwtService;
import com.clara.ClaraFuture.service.ParentService;
import com.clara.ClaraFuture.entity.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
public class OAuth2LoginController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ParentService parentService;

    // OAuth2 로그인 요청 처리 및 JWT 발급
    @GetMapping("/authorization/{provider}")
    public ResponseEntity<?> oauth2Login(@PathVariable String provider, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 부모 정보 확인 또는 생성
        Parent parent = parentService.getParentByEmail(email);
        if (parent == null) {
            parent = new Parent();
            parent.setParentName(oAuth2User.getAttribute("name"));
            parent.setEmail(email);
            parentService.createParent(parent);
        }

        // JWT와 리프레시 토큰 생성
        String accessToken = jwtService.createToken(parent.getEmail());
        String refreshToken = jwtService.createRefreshToken(parent.getEmail());

        // JWT와 리프레시 토큰을 ResponseEntity로 반환
        return ResponseEntity.ok().body(new TokenResponse(accessToken, refreshToken));
    }

    // 리프레시 토큰을 사용한 새로운 액세스 토큰 발급
    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (jwtService.validateRefreshToken(refreshToken)) {
            String email = jwtService.getEmailFromRefreshToken(refreshToken);
            String newAccessToken = jwtService.createToken(email);
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
