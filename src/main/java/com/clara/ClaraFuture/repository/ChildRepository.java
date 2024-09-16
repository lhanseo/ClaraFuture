package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findByParentId(Long parentId);
    Child findByEmail(String email);
    Child findByUniqueCode(String uniqueCode);
    Child createChild(Child child);
    Child updateChild(Long id, Child child);
    List<Child> findByParentParentId(Long parentId);

}
