package com.clara.ClaraFuture.controller;

import com.clara.ClaraFuture.entity.Child;
import com.clara.ClaraFuture.exception.ChildNotFoundException;
import com.clara.ClaraFuture.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/children")
public class ChildController {

    @Autowired
    private ChildService childService;

    // 자녀 정보 전체 업데이트 (PUT)
    @PutMapping("/update/{childId}")
    public ResponseEntity<Child> updateChild(@PathVariable Long childId, @RequestBody Child updatedChild) {
        Child child = childService.updateChild(childId, updatedChild);
        if (child == null) {
            throw new ChildNotFoundException("자녀를 찾을 수 없습니다. ID: " + childId);
        }
        return ResponseEntity.ok(child);
    }

    // 자녀 정보 부분 업데이트 (PATCH)
    @PatchMapping("/update/{childId}")
    public ResponseEntity<Child> patchChild(@PathVariable Long childId, @RequestBody Map<String, Object> updates) {
        Child updatedChild = childService.patchChild(childId, updates);
        if (updatedChild == null) {
            throw new ChildNotFoundException("자녀를 찾을 수 없습니다. ID: " + childId);
        }
        return ResponseEntity.ok(updatedChild);
    }

    // 새로운 자녀 생성
    @PostMapping("/create")
    public ResponseEntity<Child> createChild(@RequestBody Child child) {
        Child createdChild = childService.createChild(child);
        return ResponseEntity.ok(createdChild);
    }

    // 자녀 정보 조회
    @GetMapping("/{childId}")
    public ResponseEntity<Child> getChildById(@PathVariable Long childId) {
        Child child = childService.getChildById(childId);
        if (child == null) {
            throw new ChildNotFoundException("자녀를 찾을 수 없습니다. ID: " + childId);
        }
        return ResponseEntity.ok(child);
    }

    // 자녀 삭제
    @DeleteMapping("/delete/{childId}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long childId) {
        childService.deleteChild(childId);
        return ResponseEntity.noContent().build();
    }
}
