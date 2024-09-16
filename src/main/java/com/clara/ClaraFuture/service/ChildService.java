package com.clara.ClaraFuture.service;

import com.clara.ClaraFuture.entity.Child;
import com.clara.ClaraFuture.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    // 자녀 정보 전체 업데이트 (PUT)
    public Child updateChild(Long childId, Child updatedChild) {
        Optional<Child> childOptional = childRepository.findById(childId);
        if (childOptional.isPresent()) {
            Child child = childOptional.get();
            child.setChildName(updatedChild.getChildName());
            // 기타 필요한 필드 업데이트
            // 예: child.setParent(updatedChild.getParent());
            // 다른 필드가 있다면 여기에 추가
            return childRepository.save(child);
        }
        throw new RuntimeException("Child not found with id " + childId);
    }

    // 자녀 정보 부분 업데이트 (PATCH)
    public Child patchChild(Long childId, Map<String, Object> updates) {
        Optional<Child> childOptional = childRepository.findById(childId);
        if (childOptional.isPresent()) {
            Child child = childOptional.get();
            if (updates.containsKey("childName")) {
                child.setChildName((String) updates.get("childName"));
            }
            if (updates.containsKey("parent")) {
                // Assuming 'parent' is represented by Parent's ID or another identifier
                // 예를 들어, 부모의 ID가 전달된 경우:
                Long parentId = Long.valueOf(updates.get("parent").toString());
                // 부모 엔티티를 조회하고 설정하는 로직이 필요합니다.
                // 예:
                // Parent parent = parentRepository.findById(parentId)
                //     .orElseThrow(() -> new RuntimeException("Parent not found with id " + parentId));
                // child.setParent(parent);
                // 여기서는 ParentRepository가 필요하므로 ChildService에 추가로 주입해야 합니다.
            }
            // 기타 필드 업데이트
            return childRepository.save(child);
        }
        throw new RuntimeException("Child not found with id " + childId);
    }

    // 새로운 자녀 생성
    public Child createChild(Child child) {
        return childRepository.save(child);
    }

    // 자녀 정보 조회
    public Child getChildById(Long childId) {
        return childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found with id " + childId));
    }

    // 자녀 삭제
    public void deleteChild(Long childId) {
        childRepository.deleteById(childId);
    }
}
