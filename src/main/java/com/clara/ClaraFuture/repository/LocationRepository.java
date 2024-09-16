package com.clara.ClaraFuture.repository;

import com.clara.ClaraFuture.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByChildId(Long childId);
}
