package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionWithObjectListRes;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionProvider {
    private final MissionRepository missionRepository;
    private final MissionLogRepository missionLogRepository;

    public GetMissionWithObjectListRes getMission(int missionId) {
        Mission mission = missionRepository.findByIdAndIsActive(missionId, true)
                .orElseThrow(() -> new BaseException(Status.MISSION_NOT_FOUND));
        
        return EntityDtoMapper.INSTANCE.toGetMissionRes(mission);
    }

    public GetMissionLogRes getMissionLog(Integer missionCompleteLogId) {
        MissionLog missionLog = missionLogRepository.findById(missionCompleteLogId)
                .orElseThrow(
                        () -> new BaseException(Status.INTERNAL_SERVER_ERROR));

        return EntityDtoMapper.INSTANCE.toMissionLogRes(missionLog);
    }

    public List<GetMissionLogRes> getMissionCompleteLogsByUserId(Integer userId) {
        List<MissionLog> missionLogList = missionLogRepository.findAllByUserId(userId);

        return EntityDtoMapper.INSTANCE.toMissionLogResList(missionLogList);
    }
}
