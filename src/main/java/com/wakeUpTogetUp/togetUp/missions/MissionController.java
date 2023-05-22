package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.missions.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.missions.dto.response.PostObjectRecognitionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/mission")
public class MissionController {
    private final MissionProvider missionProvider;
    private final MissionService missionService;

    /**
     * 미션 목록 가져오기
     * @param isActivated
     * @return
     */
    @GetMapping("")
    BaseResponse<List<GetMissionRes>> getMissions(
            @RequestParam(required = false) boolean isActivated
    ){
        return new BaseResponse(Status.SUCCESS, missionProvider.getMissions(isActivated));
    }

    // 사물 인식 미션
    @PostMapping("/recognition/object/{object}")
    @ResponseStatus(HttpStatus.CREATED)
    BaseResponse<PostObjectRecognitionRes> recognizeObject(
            @PathVariable String object
            // TODO : rendering 하는거 추가하기
//            @RequestParam String rendering
    ){
        return new BaseResponse(Status.SUCCESS_CREATED, missionService.recognizeObject(object));
    }
}
