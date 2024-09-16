package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.Star;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Long> {
    Star findByChildId(Long childId);

    Star findByChildChildId(Long childId);

    boolean contains();
}
