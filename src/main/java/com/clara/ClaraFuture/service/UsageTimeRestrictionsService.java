package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.entity.UsageTimeRestrictions;
import com.clara.ClaraFuture.repository.UsageTimeRestrictionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsageTimeRestrictionsService {

    @Autowired
    private UsageTimeRestrictionsRepository usageTimeRestrictionsRepository;

    // 모든 사용 시간 제한을 조회
    public List<UsageTimeRestrictions> getAllUsageTimeRestrictions() {
        return usageTimeRestrictionsRepository.findAll();
    }

    // 사용 시간 제한 ID로 검색
    public UsageTimeRestrictions getUsageTimeRestrictionById(Long restrictionId) {
        return usageTimeRestrictionsRepository.findById(restrictionId)
                .orElseThrow(() -> new RuntimeException("Usage Time Restriction not found with id: " + restrictionId));
    }

    // 디바이스 ID로 사용 시간 제한 조회
    public List<UsageTimeRestrictions> getUsageTimeRestrictionsByDeviceId(Long deviceId) {
        return usageTimeRestrictionsRepository.findByDeviceDeviceId(deviceId);
    }

    // 사용 시간 제한 생성
    public UsageTimeRestrictions createUsageTimeRestriction(UsageTimeRestrictions restriction) {
        return usageTimeRestrictionsRepository.save(restriction);
    }

    // 사용 시간 제한 업데이트
    public UsageTimeRestrictions updateUsageTimeRestriction(Long restrictionId, UsageTimeRestrictions updatedRestriction) {
        UsageTimeRestrictions restriction = getUsageTimeRestrictionById(restrictionId);
        restriction.setDailyLimitMinutes(updatedRestriction.getDailyLimitMinutes());
        return usageTimeRestrictionsRepository.save(restriction);
    }

    // 사용 시간 제한 삭제
    public void deleteUsageTimeRestriction(Long restrictionId) {
        usageTimeRestrictionsRepository.deleteById(restrictionId);
    }
}
