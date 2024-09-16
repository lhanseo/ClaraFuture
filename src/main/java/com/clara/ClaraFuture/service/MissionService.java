package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.entity.Mission;
import com.clara.ClaraFuture.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    // 미션 정보 전체 업데이트 (PUT)
    public Mission updateMission(Long missionId, Mission updatedMission) {
        Optional<Mission> missionOptional = missionRepository.findById(missionId);
        if (missionOptional.isPresent()) {
            Mission mission = missionOptional.get();
            mission.setMissionName(updatedMission.getMissionName());
            mission.setDescription(updatedMission.getDescription());
            mission.setRewardStar(updatedMission.getRewardStar());
            // 기타 필요한 필드 업데이트
            return missionRepository.save(mission);
        }
        throw new RuntimeException("Mission not found with id " + missionId);
    }

    // 미션 정보 부분 업데이트 (PATCH)
    public Mission patchMission(Long missionId, Map<String, Object> updates) {
        Optional<Mission> missionOptional = missionRepository.findById(missionId);
        if (missionOptional.isPresent()) {
            Mission mission = missionOptional.get();
            if (updates.containsKey("missionName")) {
                mission.setMissionName((String) updates.get("missionName"));
            }
            if (updates.containsKey("description")) {
                mission.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("rewardStar")) {
                mission.setRewardStar((Integer) updates.get("rewardStar"));
            }
            // 기타 필드 업데이트
            // 예: if (updates.containsKey("anotherField")) { mission.setAnotherField((Type) updates.get("anotherField")); }
            return missionRepository.save(mission);
        }
        throw new RuntimeException("Mission not found with id " + missionId);
    }

    // 새로운 미션 생성
    public Mission createMission(Mission mission) {
        return missionRepository.save(mission);
    }

    // 미션 정보 조회
    public Mission getMissionById(Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found with id " + missionId));
    }

    // 미션 삭제
    public void deleteMission(Long missionId) {
        missionRepository.deleteById(missionId);
    }
}
