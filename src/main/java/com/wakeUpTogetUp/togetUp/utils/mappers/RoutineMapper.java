package com.wakeUpTogetUp.togetUp.utils.mappers;

import com.wakeUpTogetUp.togetUp.routines.model.GetRoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoutineMapper {
    RoutineMapper INSTANCE = Mappers.getMapper(RoutineMapper.class);

    @Mapping(target = "userId", expression = "java(routine.getUser().getId())")
    @Mapping(target = "missionId", expression = "java(routine.getMission().getId())")
    GetRoutineRes entityToGetRoutineRes(Routine routine);
}
