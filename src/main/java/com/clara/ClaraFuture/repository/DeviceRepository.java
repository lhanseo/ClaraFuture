package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByChildId(Long childId);
    Device createDevice(Device device);

    Device updateDevice(Long id, Device device);

    List<Device> findByChildChildId(Long childId);
}
