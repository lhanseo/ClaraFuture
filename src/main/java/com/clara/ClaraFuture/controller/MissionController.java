package com.clara.ClaraFuture.controller;

import com.clara.ClaraFuture.entity.Mission;
import com.clara.ClaraFuture.exception.MissionNotFoundException;
import com.clara.ClaraFuture.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    @Autowired
    private MissionService missionService;

    // 미션 전체 정보 업데이트 (PUT)
    @PutMapping("/update/{missionId}")
    public ResponseEntity<Mission> updateMission(@PathVariable Long missionId, @RequestBody Mission updatedMission) {
        Mission mission = missionService.updateMission(missionId, updatedMission);
        if (mission == null) {
            throw new MissionNotFoundException("미션을 찾을 수 없습니다. ID: " + missionId);
        }
        return ResponseEntity.ok(mission);
    }

    // 미션 부분 정보 업데이트 (PATCH)
    @PatchMapping("/update/{missionId}")
    public ResponseEntity<Mission> patchMission(@PathVariable Long missionId, @RequestBody Map<String, Object> updates) {
        Mission updatedMission = missionService.patchMission(missionId, updates);
        if (updatedMission == null) {
            throw new MissionNotFoundException("미션을 찾을 수 없습니다. ID: " + missionId);
        }
        return ResponseEntity.ok(updatedMission);
    }

    // 새로운 미션 생성
    @PostMapping("/create")
    public ResponseEntity<Mission> createMission(@RequestBody Mission mission) {
        Mission createdMission = missionService.createMission(mission);
        return ResponseEntity.ok(createdMission);
    }

    // 미션 정보 조회
    @GetMapping("/{missionId}")
    public ResponseEntity<Mission> getMissionById(@PathVariable Long missionId) {
        Mission mission = missionService.getMissionById(missionId);
        if (mission == null) {
            throw new MissionNotFoundException("미션을 찾을 수 없습니다. ID: " + missionId);
        }
        return ResponseEntity.ok(mission);
    }

    // 미션 삭제
    @DeleteMapping("/delete/{missionId}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long missionId) {
        missionService.deleteMission(missionId);
        return ResponseEntity.noContent().build();
    }
}
