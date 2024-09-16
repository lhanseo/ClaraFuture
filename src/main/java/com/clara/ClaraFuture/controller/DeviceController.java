package com.clara.ClaraFuture.controller;

import com.clara.ClaraFuture.entity.Device;
import com.clara.ClaraFuture.exception.ChildNotFoundException;
import com.clara.ClaraFuture.exception.DeviceNotFoundException;
import com.clara.ClaraFuture.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // 전체 디바이스 정보 업데이트 (PUT)
    @PutMapping("/update/{deviceId}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long deviceId, @RequestBody Device updatedDevice) {
        // 전체 리소스를 업데이트
        Device device = deviceService.updateDevice(deviceId, updatedDevice);
        return ResponseEntity.ok(device);
    }

    // 디바이스의 일부 필드 업데이트 (PATCH)
    @PatchMapping("/update/{deviceId}")
    public ResponseEntity<Device> patchDevice(@PathVariable Long deviceId, @RequestBody Map<String, Object> updates) {
        // 부분적으로 리소스 업데이트
        Device updatedDevice = deviceService.patchDevice(deviceId, updates);
        return ResponseEntity.ok(updatedDevice);
    }

    // 생성, 삭제, 조회 로직도 함께 포함
    // 새로운 디바이스 생성
    @PostMapping("/create")
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        Device createdDevice = deviceService.saveDevice(device);
        return ResponseEntity.ok(createdDevice);
    }

    // 디바이스 정보 조회
    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long deviceId) {
        Device device = deviceService.getDeviceById(deviceId);
        if (device == null) {
            throw new DeviceNotFoundException("기기를 찾을 수 없습니다. ID: " + deviceId);
        }
        return ResponseEntity.ok(device);
    }

    // 디바이스 삭제
    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.noContent().build();
    }

    // 자녀 ID로 디바이스 목록 조회
    @GetMapping("/child/{childId}")
    public ResponseEntity<List<Device>> getDevicesByChildId(@PathVariable Long childId) {
        List<Device> devices = deviceService.getDevicesByChildId(childId);
        if (devices == null) {
            throw new ChildNotFoundException("자녀를 찾을 수 없습니다. ID: " + childId);
        }
        return ResponseEntity.ok(devices);
    }

    // 디바이스 제한 시간 설정 (사용 시간 제한)
    @PostMapping("/set-restriction/{deviceId}")
    public ResponseEntity<Device> setUsageRestriction(@PathVariable Long deviceId, @RequestParam int limitMinutes) {
        Device updatedDevice = deviceService.setUsageRestriction(deviceId, limitMinutes);
        return ResponseEntity.ok(updatedDevice);
    }
}
