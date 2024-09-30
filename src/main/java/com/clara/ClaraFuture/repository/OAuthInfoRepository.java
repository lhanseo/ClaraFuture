package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.OAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthInfoRepository extends JpaRepository<OAuthInfo, Long> {
    OAuthInfo findByProviderAndProviderId(String provider, String providerId);
    Optional<OAuthInfo> findByEmail(String email);

    Optional<OAuthInfo> findByEmailAndProvider(String email, String google);
}
