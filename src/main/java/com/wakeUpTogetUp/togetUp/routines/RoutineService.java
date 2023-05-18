package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.missions.MissionRepository;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.routines.dto.request.PatchRoutineReq;
import com.wakeUpTogetUp.togetUp.routines.dto.request.PostRoutineReq;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.mappers.RoutineMapper;
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
    public int createRoutine(Integer userId, PostRoutineReq postRoutineReq) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_USER_ID)
        );

        Mission mission = missionRepository.findById(postRoutineReq.getMissionId()).orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_MISSION_ID)
        );

        Routine routine = Routine.builder()
                .user(user)
                .mission(mission)
                .name(postRoutineReq.getName())
                .estimatedTime(postRoutineReq.getEstimatedTime())
                .icon(postRoutineReq.getIcon())
                .color(postRoutineReq.getColor())
                .build();

        routineRepository.save(routine);

        return routine.getId();
    }

    // 루틴 수정
    public RoutineRes updateRoutine(Integer routineId, PatchRoutineReq patchRoutineReq) {
        patchRoutineReq.setId(routineId);

        Routine routine = routineRepository.findById(routineId).orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_ROUTINE_ID)
        );

        routine.modifyProperties(
                missionRepository.findById(patchRoutineReq.getMissionId()).orElseThrow(
                        () -> new BaseException(ResponseStatus.INVALID_MISSION_ID)
                ),
                patchRoutineReq.getName(),
                patchRoutineReq.getEstimatedTime(),
                patchRoutineReq.getIcon(),
                patchRoutineReq.getColor()
        );

        Routine routineModified = routineRepository.save(routine);

        RoutineRes patchRoutineRes = RoutineMapper.INSTANCE.toRoutineRes(routineModified);

        return patchRoutineRes;
    }

    // 루틴 삭제
    public void deleteRoutine(Integer routineId) {
        routineRepository.deleteById(routineId);
    }
}
