package com.clara.ClaraFuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppInstallRequestRepository extends JpaRepository<AppInstallRequest, Long> {
    List<AppInstallRequest> findByDeviceId(Long deviceId);
}
