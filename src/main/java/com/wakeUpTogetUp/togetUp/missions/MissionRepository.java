package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    @Override
    Optional<Mission> findById(Integer id);
}
