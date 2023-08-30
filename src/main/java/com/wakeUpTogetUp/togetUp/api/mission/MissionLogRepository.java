package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionLogRepository extends JpaRepository<MissionLog, Integer> {
    @Query("SELECT MCL FROM MissionLog MCL WHERE MCL.user.id = :userId")
    List<MissionLog> findAllByUserId(@Param("userId") Integer userId);

    @Query("SELECT MSL " +
            "FROM MissionLog MSL " +
            "WHERE (MSL.room.id, MSL.createdAt) IN " +
            "(SELECT ML.room.id, MAX(ML.createdAt) " +
            "FROM MissionLog ML " +
            "WHERE ML.room.id IN :roomIds " +
            "GROUP BY ML.room.id)")
    List<MissionLog> findMostRecentMissionLogsByRoomIds(@Param("roomIds") List<Integer> roomIds);






}
