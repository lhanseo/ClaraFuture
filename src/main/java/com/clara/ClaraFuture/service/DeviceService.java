package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.entity.Device;
import com.clara.ClaraFuture.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    // 전체 디바이스 정보 업데이트 (PUT)
    public Device updateDevice(Long deviceId, Device updatedDevice) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);

        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            // 전체 업데이트. 제공된 모든 필드를 업데이트.
            device.setDeviceType(updatedDevice.getDeviceType());
            device.setTotalUsageLimit(updatedDevice.getTotalUsageLimit());
            // 필요에 따라 다른 필드도 업데이트
            return deviceRepository.save(device);  // save는 새로운 데이터를 업데이트 혹은 저장
        }
        throw new RuntimeException("Device not found with id " + deviceId);
    }

    // 디바이스의 일부 필드 업데이트 (PATCH)
    public Device patchDevice(Long deviceId, Map<String, Object> updates) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);

        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();

            // 부분 업데이트. 필드별로 값이 제공되었는지 확인 후 업데이트.
            if (updates.containsKey("deviceType")) {
                device.setDeviceType((String) updates.get("deviceType"));
            }
            if (updates.containsKey("totalUsageLimit")) {
                device.setTotalUsageLimit((Integer) updates.get("totalUsageLimit"));
            }
            // 필요에 따라 더 많은 필드를 업데이트할 수 있음

            return deviceRepository.save(device);  // 부분적으로 업데이트한 데이터 저장
        }
        throw new RuntimeException("Device not found with id " + deviceId);
    }

    // 새로운 디바이스 생성
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);  // 새 디바이스 생성 및 저장
    }

    // 디바이스 정보 조회
    public Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found with id " + deviceId));
    }

    // 디바이스 삭제
    public void deleteDevice(Long deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    // 자녀 ID로 디바이스 목록 조회
    public List<Device> getDevicesByChildId(Long childId) {

        return deviceRepository.findByChildChildId(childId);
    }

    // 디바이스 제한 시간 설정 (사용 시간 제한)
    public Device setUsageRestriction(Long deviceId, int limitMinutes) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);

        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            device.setTotalUsageLimit(limitMinutes);
            return deviceRepository.save(device);  // 제한 시간 설정 후 저장
        }
        throw new RuntimeException("Device not found with id " + deviceId);
    }
}
