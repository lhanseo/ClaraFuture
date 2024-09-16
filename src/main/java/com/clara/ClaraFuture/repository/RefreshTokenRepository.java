package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // 토큰으로 리프레시 토큰 검색
    Optional<RefreshToken> findByToken(String token);

    // 부모 ID로 리프레시 토큰 삭제
    void deleteByParentParentId(Long parentId);
}
