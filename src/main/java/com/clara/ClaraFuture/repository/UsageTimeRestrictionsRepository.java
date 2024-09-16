package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.UsageTimeRestrictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageTimeRestrictionsRepository extends JpaRepository<UsageTimeRestrictions, Long> {
    // ID로 검색
    UsageTimeRestrictions findByRestrictionId(Long restrictionId);

    // 디바이스 ID로 사용 시간 제한 검색
    List<UsageTimeRestrictions> findByDeviceDeviceId(Long deviceId);
}
