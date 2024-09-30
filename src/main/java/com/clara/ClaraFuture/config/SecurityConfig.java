package com.clara.ClaraFuture.config;

import com.clara.ClaraFuture.security.JwtAuthenticationFilter;
import com.clara.ClaraFuture.security.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login/**", "/oauth2/**","/oauth2/token/refresh").permitAll() // 로그인 및 소셜 인증 경로 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2AuthenticationSuccessHandler)); // 성공 시 JWT 발급

        // JWT 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
