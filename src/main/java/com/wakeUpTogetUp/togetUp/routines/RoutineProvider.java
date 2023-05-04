package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.exception.ResponseStatus;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.utils.mappers.RoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineProvider {
    private final RoutineRepository routineRepository;
    public RoutineRes getRoutine(Integer routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(
                        () -> new BaseException(ResponseStatus.INVALID_ROUTINE_ID)
                );

        RoutineRes routineRes = RoutineMapper.INSTANCE.toGetRoutineRes(routine);

        return routineRes;
    }

    public List<RoutineRes> getRoutinesByUserId(Integer userId) {
        List<Routine> routineList = routineRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new BaseException(ResponseStatus.ROUTINE_NOT_FOUND)
                );

        ArrayList<RoutineRes> routineResList = new ArrayList<>();
        for(Routine routine : routineList) {
            routineResList.add(RoutineMapper.INSTANCE.toGetRoutineRes(routine));
        }

        return routineResList;
    }
}
