package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findByEmail(String email);
    Parent findByUniqueCode(String uniqueCode);
    Parent updateParent(Long id, Parent parent);

    Parent createParent(Parent parent);
}
