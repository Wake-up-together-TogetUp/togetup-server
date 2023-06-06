package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineProvider {
    private final RoutineRepository routineRepository;
    public RoutineRes getRoutine(Integer routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_ROUTINE_ID)
                );

        return EntityDtoMapper.INSTANCE.toRoutineRes(routine);
    }

    public List<RoutineRes> getRoutinesByAlarmId(Integer alarmId){
        List<Routine> routineList = routineRepository.findAllByAlarmId(alarmId, Sort.by("routineOrder"));

        return EntityDtoMapper.INSTANCE.toRoutineResList(routineList);
    }
}
