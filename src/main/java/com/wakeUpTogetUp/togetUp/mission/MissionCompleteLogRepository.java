package com.wakeUpTogetUp.togetUp.mission;

import com.wakeUpTogetUp.togetUp.mission.model.MissionCompleteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionCompleteLogRepository extends JpaRepository<MissionCompleteLog, Integer> {
    @Query("SELECT MCL FROM MissionCompleteLog MCL WHERE MCL.user.id = :userId")
    List<MissionCompleteLog> findAllByUserId(@Param("userId") Integer userId);
}
