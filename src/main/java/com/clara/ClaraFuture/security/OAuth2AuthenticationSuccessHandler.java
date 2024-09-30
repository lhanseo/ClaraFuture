package com.clara.ClaraFuture.security;

import com.clara.ClaraFuture.entity.OAuthInfo;
import com.clara.ClaraFuture.entity.Parent;
import com.clara.ClaraFuture.service.JwtService;
import com.clara.ClaraFuture.service.OAuthService;
import com.clara.ClaraFuture.service.ParentService;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final OAuthService oAuthService;
    private final ParentService parentService;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(JwtService jwtService, OAuthService oAuthService, ParentService parentService) {
        this.jwtService = jwtService;
        this.oAuthService = oAuthService;
        this.parentService = parentService;
    }

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request,
                                        jakarta.servlet.http.HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String provider = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority()) // 소셜로그인 사용자 정보 추출
                .orElse("UNKNOWN");
        String providerId = oAuth2User.getName();

        // OAuthInfo 엔티티 처리
        Optional<OAuthInfo> optionalOAuthInfo = oAuthService.getOAuthInfoByEmail(email);
        if (optionalOAuthInfo.isEmpty()) {
            // 처음 로그인한 사용자이므로 OAuthInfo 저장
            Parent parent = new Parent();
            parent = parentService.getParentByEmail(email);

            OAuthInfo oAuthInfo = new OAuthInfo();
            oAuthInfo.setEmail(email);
            oAuthInfo.setParent(parent);
            oAuthInfo.setProvider(provider);
            oAuthInfo.setProviderId(providerId);
            oAuthService.saveOAuthInfo(oAuthInfo);
        }

        // JWT 토큰 생성
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken(email);

        // 토큰을 응답으로 전달
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(tokens);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
