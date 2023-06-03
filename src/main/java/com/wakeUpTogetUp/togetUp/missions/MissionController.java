package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.files.FileService;
import com.wakeUpTogetUp.togetUp.missions.dto.request.PostMissionCompleteLogReq;
import com.wakeUpTogetUp.togetUp.missions.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.missions.dto.response.PostObjectRecognitionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/mission")
public class MissionController {
    private final MissionProvider missionProvider;
    private final MissionService missionService;
    private final FileService fileService;

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

    /**
     * 사물 인식 미션
     * @param missionImage
     * @param object
     * @return
     * @throws Exception
     */
    @PostMapping("/detection/object/{object}")
    @ResponseStatus(HttpStatus.CREATED)
    BaseResponse<PostObjectRecognitionRes> recognizeObject(
            @RequestPart MultipartFile missionImage,
            @PathVariable String object
            // TODO : rendering 추가
//            @RequestParam String rendering
    ) throws Exception {

        missionService.recognizeObject(object, missionImage);
        // TODO : 렌더링

        String filePath = fileService.uploadFile(missionImage, "mission");
        return new BaseResponse(Status.MISSION_SUCCESS, new PostObjectRecognitionRes(filePath));
    }

    // 미션 성공 기록
    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.CREATED)
    BaseResponse missionComplete(
            @RequestBody PostMissionCompleteLogReq postMissionCompleteHistoryReq
    ){
        // 기록

        return new BaseResponse(Status.SUCCESS_CREATED);
    }
}
