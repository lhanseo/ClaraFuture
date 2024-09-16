package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.entity.RefreshToken;
import com.clara.ClaraFuture.entity.Parent;
import com.clara.ClaraFuture.repository.RefreshTokenRepository;
import com.clara.ClaraFuture.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ParentRepository parentRepository;

    // 리프레시 토큰 생성
    public RefreshToken createRefreshToken(Long parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found with id: " + parentId));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString()); // 랜덤한 토큰 생성
        refreshToken.setParent(parent);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7)); // 7일 후 만료
        return refreshTokenRepository.save(refreshToken);
    }

    // 리프레시 토큰으로 검색
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    // 리프레시 토큰 만료 여부 확인
    public boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isBefore(LocalDateTime.now());
    }

    // 리프레시 토큰 삭제
    public void deleteRefreshToken(Long parentId) {
        refreshTokenRepository.deleteByParentParentId(parentId);
    }

    // 리프레시 토큰 갱신 (새로운 토큰 발급)
    public RefreshToken refreshExistingToken(String oldToken) {
        Optional<RefreshToken> optionalToken = refreshTokenRepository.findByToken(oldToken);
        if (optionalToken.isPresent()) {
            RefreshToken existingToken = optionalToken.get();
            existingToken.setToken(UUID.randomUUID().toString()); // 새로운 토큰으로 교체
            existingToken.setExpiryDate(LocalDateTime.now().plusDays(7)); // 만료 시간 갱신
            return refreshTokenRepository.save(existingToken);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
}
