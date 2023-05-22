package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineProvider {
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    public RoutineRes getRoutine(Integer routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_ROUTINE_ID)
                );

        return EntityDtoMapper.INSTANCE.toRoutineRes(routine);
    }

    public List<RoutineRes> getRoutinesByUserId(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_USER_ID)
                );

        List<Routine> routineList = routineRepository.findByUserId(userId);

        return EntityDtoMapper.INSTANCE.toRoutineResList(routineList);
    }

    // alarmId로 routineRes 리스트 가져오기
    public List<RoutineRes> getRoutineResByAlarmId(Integer alarmId){
        List<Routine> routineList = routineRepository.findByAlarmId(alarmId);

        return EntityDtoMapper.INSTANCE.toRoutineResList(routineList);
    }
}
