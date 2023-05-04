package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.missions.MissionRepository;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.routines.dto.request.RoutineReq;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;

    // 루틴 생성
    @Transactional
    public int createRoutine(Integer userId, RoutineReq routineReq) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_USER_ID)
        );

        Mission mission = missionRepository.findById(routineReq.getMissionId()).orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_MISSION_ID)
        );

        Routine routine = Routine.builder()
                .user(user)
                .mission(mission)
                .name(routineReq.getName())
                .estimatedTime(routineReq.getEstimatedTime())
                .icon(routineReq.getIcon())
                .color(routineReq.getColor())
                .build();

        routineRepository.save(routine);

        return routine.getId();
    }
}
