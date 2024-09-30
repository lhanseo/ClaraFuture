package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.entity.OAuthInfo;
import com.clara.ClaraFuture.entity.Parent;
import com.clara.ClaraFuture.repository.OAuthInfoRepository;
import com.clara.ClaraFuture.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class OAuthService {

    @Autowired
    private OAuthInfoRepository oauthInfoRepository;

    @Autowired
    private ParentRepository parentRepository;

    // OAuth 사용자 정보 저장
    public OAuthInfo saveOAuthInfo(OAuthInfo oAuthInfo) {
        return oauthInfoRepository.save(oAuthInfo);
    }

    // 고유 코드 생성 로직
    private String generateUniqueCode() {
        int length = 8; // 고유 코드의 길이
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 대문자 영어와 숫자의 조합
        Random random = new Random();
        StringBuilder uniqueCode = new StringBuilder();

        for (int i = 0; i < length; i++) {
            uniqueCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        return uniqueCode.toString();
    }

    // OAuth 정보로 부모 찾기
    public Optional<OAuthInfo> findByProviderAndProviderId(String provider, String providerId) {
        return Optional.ofNullable(oauthInfoRepository.findByProviderAndProviderId(provider, providerId));
    }

    // 부모 이메일로 OAuth 정보 찾기
    public Optional<OAuthInfo> getOAuthInfoByEmail(String email) {
        return oauthInfoRepository.findByEmail(email);
    }
}
