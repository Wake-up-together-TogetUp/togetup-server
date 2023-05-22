package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.missions.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionProvider {
    private final MissionRepository missionRepository;

    public List<GetMissionRes> getMissions(Boolean isActivated) {
        List<Mission> missionList;

        if(isActivated)
            missionList = missionRepository.findAllTrue(Sort.by("id"));
        else
            missionList = missionRepository.findAll(Sort.by("id"));

        return EntityDtoMapper.INSTANCE.toGetMissionResList(missionList);
    }
}
