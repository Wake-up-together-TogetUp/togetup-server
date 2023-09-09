package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionLogRes;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionProvider {
    private final MissionRepository missionRepository;
    private final MissionLogRepository missionLogRepository;

    public GetMissionRes getObjectDetectionMissions() {
        Mission mission = missionRepository.findAllByIdAndIsActive(2, true);
        return EntityDtoMapper.INSTANCE.toGetMissionRes(mission);
    }

    public GetMissionRes getFaceRecognitionMissions() {
        Mission mission = missionRepository.findAllByIdAndIsActive(3, true);
        return EntityDtoMapper.INSTANCE.toGetMissionRes(mission);
    }

    public MissionLogRes getMissionLog(Integer missionCompleteLogId){
        MissionLog missionLog = missionLogRepository.findById(missionCompleteLogId)
                .orElseThrow(
                        () -> new BaseException(Status.INTERNAL_SERVER_ERROR));

        return EntityDtoMapper.INSTANCE.toMissionLogRes(missionLog);
    }

    public List<MissionLogRes> getMissionCompleteLogsByUserId(Integer userId) {
        List<MissionLog> missionLogList = missionLogRepository.findAllByUserId(userId);

        return EntityDtoMapper.INSTANCE.toMissionLogResList(missionLogList);
    }
}
