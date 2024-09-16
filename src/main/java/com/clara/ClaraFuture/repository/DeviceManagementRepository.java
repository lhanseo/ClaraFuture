package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.DeviceManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceManagementRepository extends JpaRepository<DeviceManagement, Long> {
    List<DeviceManagement> findByDeviceId(Long deviceId);
}
