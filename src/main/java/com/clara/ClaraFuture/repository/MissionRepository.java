package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByChildId(Long childId);

    List<Mission> findByChildChildId(Long childId);
}
