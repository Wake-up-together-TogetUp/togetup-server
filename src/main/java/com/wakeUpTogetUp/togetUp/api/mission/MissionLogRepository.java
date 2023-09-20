package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MissionLogRepository extends JpaRepository<MissionLog, Integer> {
    @Query("SELECT MCL FROM MissionLog MCL WHERE MCL.user.id = :userId")
    List<MissionLog> findAllByUserId(@Param("userId") Integer userId);


    List<MissionLog> findAllByRoom_IdAndCreatedAtContaining(Integer roomId, LocalDate localDate);


}
