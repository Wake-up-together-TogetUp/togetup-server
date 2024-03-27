package com.wakeUpTogetUp.togetUp.api.mission.repository;

import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    @Query("select m from Mission m where m.isActive = true")
    List<Mission> findAllTrue(Sort sort);

    Optional<Mission> findByIdAndIsActive(Integer id, Boolean isActive);
}
