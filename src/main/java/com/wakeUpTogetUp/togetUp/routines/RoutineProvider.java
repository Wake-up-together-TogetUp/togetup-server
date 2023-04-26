package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.routines.model.GetRoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutineProvider {
    private final RoutineDao routineDao;
    public GetRoutineRes getRoutine(Integer routineId) {
        Routine routine = routineDao.findById(routineId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_ROUTINE_ID)
        );

        GetRoutineRes getRoutineRes = GetRoutineRes.builder()
                .id(routine.getId())
                .userId(routine.getUser().getId())
                .missionId(routine.getMission().getId())
                .name(routine.getName())
                .estimatedTime(routine.getEstimatedTime())
                .icon(routine.getIcon())
                .color(routine.getColor())
                .build();

        return getRoutineRes;
    }
}
