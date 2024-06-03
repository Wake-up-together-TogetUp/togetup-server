package com.wakeUpTogetUp.togetUp.api.mission.repository;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionObjectRepository extends JpaRepository<MissionObject, Integer> {

    boolean existsByMission_NameAndName(String missionName, String name);
}