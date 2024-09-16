package com.clara.ClaraFuture.controller;

import com.clara.ClaraFuture.entity.Parent;
import com.clara.ClaraFuture.exception.ParentNotFoundException;
import com.clara.ClaraFuture.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;

    // 부모 정보 업데이트 (PUT)
    @PutMapping("/update/{parentId}")
    public ResponseEntity<Parent> updateParent(@PathVariable Long parentId, @RequestBody Parent updatedParent) {
        Parent parent = parentService.updateParent(parentId, updatedParent);
        if (parent == null) {
            throw new ParentNotFoundException("부모를 찾을 수 없습니다 ID : " + parentId);
        }
        return ResponseEntity.ok(parent);
    }

    // 부모 정보 부분 업데이트 (PATCH)
    @PatchMapping("/update/{parentId}")
    public ResponseEntity<Parent> patchParent(@PathVariable Long parentId, @RequestBody Map<String, Object> updates) {
        Parent updatedParent = parentService.patchParent(parentId, updates);
        if (updatedParent == null) {
            throw new ParentNotFoundException("부모를 찾을 수 없습니다 ID : " + parentId);
        }
        return ResponseEntity.ok(updatedParent);
    }

    // 새로운 부모 생성
    @PostMapping("/create")
    public ResponseEntity<Parent> createParent(@RequestBody Parent parent) {
        Parent createdParent = parentService.createParent(parent);
        return ResponseEntity.ok(createdParent);
    }

    // 부모 정보 조회
    @GetMapping("/{parentId}")
    public ResponseEntity<Parent> getParentById(@PathVariable Long parentId) {
        Parent parent = parentService.getParentById(parentId);
        if (parent == null) {
            throw new ParentNotFoundException("부모를 찾을 수 없습니다 ID : " + parentId);
        }
        return ResponseEntity.ok(parent);
    }

    // 부모 삭제
    @DeleteMapping("/delete/{parentId}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long parentId) {
        parentService.deleteParent(parentId);
        return ResponseEntity.noContent().build();
    }
}
