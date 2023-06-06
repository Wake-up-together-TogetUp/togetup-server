package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.missions.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.missions.dto.response.MissionCompleteLogRes;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.missions.model.MissionCompleteLog;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionProvider {
    private final MissionRepository missionRepository;
    private final MissionCompleteLogRepository missionCompleteLogRepository;

    public List<GetMissionRes> getMissions(Boolean isActivated) {
        List<Mission> missionList;

        if(isActivated)
            missionList = missionRepository.findAllTrue(Sort.by("id"));
        else
            missionList = missionRepository.findAll(Sort.by("id"));

        return EntityDtoMapper.INSTANCE.toGetMissionResList(missionList);
    }

    public MissionCompleteLogRes getMissionCompleteLog(Integer missionCompleteLogId){
        MissionCompleteLog missionCompleteLog = missionCompleteLogRepository.findById(missionCompleteLogId)
                .orElseThrow(
                        () -> new BaseException(Status.INTERNAL_SERVER_ERROR));

        return EntityDtoMapper.INSTANCE.toMissionCompleteLogRes(missionCompleteLog);
    }

    public List<MissionCompleteLogRes> getMissionCompleteLogsByUserId(Integer userId) {
        List<MissionCompleteLog> missionCompleteLogList = missionCompleteLogRepository.findAllByUserId(userId);

        return EntityDtoMapper.INSTANCE.toMissionCompleteLogResList(missionCompleteLogList);
    }
}
