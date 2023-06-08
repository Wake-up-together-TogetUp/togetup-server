package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmsRes;
import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.mission.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.mission.dto.response.MissionCompleteLogRes;
import com.wakeUpTogetUp.togetUp.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.mission.model.MissionCompleteLog;
import com.wakeUpTogetUp.togetUp.routine.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routine.model.Routine;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface EntityDtoMapper {
    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    // Alarm
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "missionId", source = "mission.id")
    AlarmsRes toAlarmsRes(Alarm alarm);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "missionId", source = "mission.id")
    AlarmRes toAlarmRes(Alarm alarm);

    // Mission
    GetMissionRes toGetMissionRes(Mission mission);
    List<GetMissionRes> toGetMissionResList(List<Mission> missionList);


    // Routine
    @Mapping(target = "alarmId", source = "alarm.id")
    RoutineRes toRoutineRes(Routine routine);
    List<RoutineRes> toRoutineResList(List<Routine> routineList);

    // MissionCompleteLog
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "alarmId", source = "alarm.id")
    MissionCompleteLogRes toMissionCompleteLogRes(MissionCompleteLog missionCompleteLog);
    List<MissionCompleteLogRes> toMissionCompleteLogResList(List<MissionCompleteLog> missionCompleteLogList);
}
