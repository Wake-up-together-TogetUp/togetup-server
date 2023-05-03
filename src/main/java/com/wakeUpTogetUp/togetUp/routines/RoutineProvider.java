package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.routines.model.GetRoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.utils.mappers.RoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineProvider {
    private final RoutineDao routineDao;
    public GetRoutineRes getRoutine(Integer routineId) {
        Routine routine = routineDao.findById(routineId)
                .orElseThrow(
                        () -> new BaseException(BaseResponseStatus.INVALID_ROUTINE_ID)
                );

        GetRoutineRes getRoutineRes = RoutineMapper.INSTANCE.entityToGetRoutineRes(routine);

        return getRoutineRes;
    }

    public List<GetRoutineRes> getRoutinesByUserId(Integer userId) {
        List<Routine> routineList = routineDao.findByUserId(userId)
                .orElseThrow(
                        () -> new BaseException(BaseResponseStatus.ROUTINE_NOT_FOUND)
                );

        ArrayList<GetRoutineRes> getRoutineResList = new ArrayList<>();
        for(Routine routine : routineList) {
            getRoutineResList.add(RoutineMapper.INSTANCE.entityToGetRoutineRes(routine));
        }

        return getRoutineResList;
    }
}
