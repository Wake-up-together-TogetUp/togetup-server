package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.missions.MissionDao;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.routines.model.PostRoutineReq;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineDao routineDao;
    private final UserRepository userRepository;
    private final MissionDao missionDao;

    // 루틴 생성
    @Transactional
    public int createRoutine(int userId, PostRoutineReq postRoutineReq) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_USER_ID)
        );

        Mission mission = missionDao.findById(postRoutineReq.getMissionId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_MISSION_ID)
        );

        Routine routine = Routine.builder()
                .user(user)
                .mission(mission)
                .name(postRoutineReq.getName())
                .estimatedTime(postRoutineReq.getEstimatedTime())
                .icon(postRoutineReq.getIcon())
                .color(postRoutineReq.getColor())
                .build();

        routineDao.save(routine);

        return routine.getId();
    }
}
