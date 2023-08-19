package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.file.FileService;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.PostMissionLogReq;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.PostObjectRecognitionRes;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/mission")
public class MissionController {
    private final JwtService jwtService;
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
     * @param objectName
     * @return
     * @throws Exception
     */
    @PostMapping("/detection/object/{objectName}")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<PostObjectRecognitionRes> recognizeObject(
            @RequestPart MultipartFile missionImage,
            @PathVariable String objectName
            // TODO : rendering 추가
//            @RequestParam String rendering
    ) throws Exception {
        missionService.recognizeObject(objectName, missionImage);
        // TODO : 렌더링

        String filePath = fileService.uploadFile(missionImage, "mission");
        return new BaseResponse(Status.MISSION_SUCCESS, new PostObjectRecognitionRes(filePath));
    }

    // 미션 성공 기록 가져오기
    // TODO : 달별 검색
    // TODO : 일별 검색
    @GetMapping("/logs")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MissionLogRes>> getMissionCompleteLogsByUserId(
            @RequestParam Integer userId
    ) {
        return new BaseResponse(Status.SUCCESS, missionProvider.getMissionCompleteLogsByUserId(userId));
    }

    // 미션 성공 기록 생성
    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<MissionLogRes> missionComplete(
            @RequestBody PostMissionLogReq postMissionCompleteHistoryReq
    ){
        Integer userId = postMissionCompleteHistoryReq.getUserId();

        if(jwtService.validateByUserId(userId)) {
            int id = missionService.createMissionCompleteLog(postMissionCompleteHistoryReq);

            return new BaseResponse(Status.SUCCESS_CREATED, missionProvider.getMissionCompleteLog(id));
        }
        else
            throw new BaseException(Status.JWT_MISMATCH);
    }
    
    // 미션 성공 기록 삭제
}
