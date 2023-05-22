package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.missions.dto.GetMissionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/mission")
public class MissionController {
    private final MissionProvider missionProvider;
    @GetMapping("")
    BaseResponse<List<GetMissionRes>> getMissions(){
        return new BaseResponse(Status.SUCCESS, missionProvider.getMissions());
    }
}
