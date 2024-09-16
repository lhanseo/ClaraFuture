package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.entity.Parent;
import com.clara.ClaraFuture.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    // 전체 부모 정보 업데이트 (PUT)
    public Parent updateParent(Long parentId, Parent updatedParent) {
        Optional<Parent> parentOptional = parentRepository.findById(parentId);
        if (parentOptional.isPresent()) {
            Parent parent = parentOptional.get();
            parent.setParentName(updatedParent.getParentName());
            parent.setEmail(updatedParent.getEmail());
            parent.setPasswordHash(updatedParent.getPasswordHash());
            // 기타 필요한 필드 업데이트
            return parentRepository.save(parent);
        }
        throw new RuntimeException("Parent not found with id " + parentId);
    }

    // 부모 정보 부분 업데이트 (PATCH)
    public Parent patchParent(Long parentId, Map<String, Object> updates) {
        Optional<Parent> parentOptional = parentRepository.findById(parentId);
        if (parentOptional.isPresent()) {
            Parent parent = parentOptional.get();
            if (updates.containsKey("parentName")) {
                parent.setParentName((String) updates.get("parentName"));
            }
            if (updates.containsKey("email")) {
                parent.setEmail((String) updates.get("email"));
            }
            if (updates.containsKey("passwordHash")) {
                parent.setPasswordHash((String) updates.get("passwordHash"));
            }
            // 기타 필드 업데이트
            return parentRepository.save(parent);
        }
        throw new RuntimeException("Parent not found with id " + parentId);
    }

    // 새로운 부모 생성
    public Parent createParent(Parent parent) {
        return parentRepository.save(parent);
    }

    // 부모 정보 조회
    public Parent getParentById(Long parentId) {
        return parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found with id " + parentId));
    }

    // 부모 삭제
    public void deleteParent(Long parentId) {
        parentRepository.deleteById(parentId);
    }

    // 이메일로 부모 정보 조회
    public Parent getParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    // 고유 코드로 부모 정보 조회
    public Parent getParentByUniqueCode(String uniqueCode) {
        return parentRepository.findByUniqueCode(uniqueCode);
    }
}
