package com.clara.ClaraFuture.controller;

import com.clara.ClaraFuture.entity.UsageTimeRestrictions;
import com.clara.ClaraFuture.exception.DeviceNotFoundException;
import com.clara.ClaraFuture.service.UsageTimeRestrictionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usage-time-restrictions")
public class UsageTimeRestrictionsController {

    @Autowired
    private UsageTimeRestrictionsService usageTimeRestrictionsService;

    // 새로운 사용 시간 제한 생성
    @PostMapping("/create")
    public ResponseEntity<UsageTimeRestrictions> createUsageTimeRestriction(@RequestBody UsageTimeRestrictions restriction) {
        UsageTimeRestrictions createdRestriction = usageTimeRestrictionsService.createUsageTimeRestriction(restriction);
        return ResponseEntity.ok(createdRestriction);
    }

    // 특정 사용 시간 제한 조회
    @GetMapping("/{restrictionId}")
    public ResponseEntity<UsageTimeRestrictions> getUsageTimeRestrictionById(@PathVariable Long restrictionId) {
        UsageTimeRestrictions restriction = usageTimeRestrictionsService.getUsageTimeRestrictionById(restrictionId);
        return ResponseEntity.ok(restriction);
    }

    // 전체 사용 시간 제한 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<UsageTimeRestrictions>> getAllUsageTimeRestrictions() {
        List<UsageTimeRestrictions> restrictions = usageTimeRestrictionsService.getAllUsageTimeRestrictions();
        return ResponseEntity.ok(restrictions);
    }

    // 특정 디바이스에 대한 사용 시간 제한 목록 조회
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<UsageTimeRestrictions>> getUsageTimeRestrictionsByDeviceId(@PathVariable Long deviceId) {
        List<UsageTimeRestrictions> restrictions = usageTimeRestrictionsService.getUsageTimeRestrictionsByDeviceId(deviceId);
        if (restrictions == null) {
            throw new DeviceNotFoundException("기기를 찾을 수 없습니다. ID: " + deviceId);
        }
        return ResponseEntity.ok(restrictions);
    }

    // 사용 시간 제한 업데이트 (PUT, PATCH 구분하지 않음)
    @PatchMapping("/update/{restrictionId}")
    public ResponseEntity<UsageTimeRestrictions> updateUsageTimeRestriction(
            @PathVariable Long restrictionId,
            @RequestBody UsageTimeRestrictions updatedRestriction) {
        UsageTimeRestrictions restriction = usageTimeRestrictionsService.updateUsageTimeRestriction(restrictionId, updatedRestriction);
        if (restriction == null) {
            throw new DeviceNotFoundException("사용시간를 찾을 수 없습니다. ID: " + restrictionId);
        }
        return ResponseEntity.ok(restriction);
    }

    @PatchMapping("/update/{deviceId}")
    public ResponseEntity<UsageTimeRestrictions> updateUsageTimeRestrictionbyDeviceId(
            @PathVariable Long deviceId,
            @RequestBody UsageTimeRestrictions updatedRestriction) {
        UsageTimeRestrictions restriction = usageTimeRestrictionsService.updateUsageTimeRestriction(deviceId, updatedRestriction);
        if (restriction == null) {
            throw new DeviceNotFoundException("기기를 찾을 수 없습니다. ID: " + deviceId);
        }
        return ResponseEntity.ok(restriction);
    }

    // 사용 시간 제한 삭제
    @DeleteMapping("/delete/{restrictionId}")
    public ResponseEntity<Void> deleteUsageTimeRestriction(@PathVariable Long restrictionId) {
        usageTimeRestrictionsService.deleteUsageTimeRestriction(restrictionId);
        return ResponseEntity.noContent().build();
    }
}
