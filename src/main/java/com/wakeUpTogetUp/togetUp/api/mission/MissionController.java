package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.ObjectDetectionRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.api.file.FileService;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.PostMissionLogReq;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionWithObjectListRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.PostPerformMissionRes;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.ImageProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "미션(Mission)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/mission")
public class MissionController {

    private final MissionProvider missionProvider;
    private final MissionService missionService;
    private final FileService fileService;
    private final ImageProcessor imageProcessor;

    @Operation(summary = "미션 목록 가져오기")
    @GetMapping("/{missionId}")
    BaseResponse<GetMissionWithObjectListRes> getObjectDetectionMissions(
            @Parameter(required = true, description = "- 객체인식 : 2\n- 표정인식 : 3") @PathVariable(value = "missionId") int missionId
    ) {
        return new BaseResponse<>(Status.SUCCESS, missionProvider.getMission(missionId));
    }

    // TODO : 걸린 시간 계산 aop 만들기
    @Operation(summary = "객체 탐지 미션")
    @PostMapping(value = "/object-detection/{object}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<PostPerformMissionRes> recognizeObject(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @Parameter(required = true, description = "미션 수행 사진") @RequestPart MultipartFile missionImage,
            @Parameter(required = true, description = "탐지할 객체") @PathVariable String object
    ) throws Exception {
        System.out.println("\n[객체 탐지 미션 api 시작]");
        long startTime = System.currentTimeMillis();

        String filePath;

        // 이미지 형식 검사 : jpg
        if (Objects.equals(missionImage.getContentType(), MediaType.IMAGE_JPEG_VALUE)) {
            ObjectDetectionRes odr = missionService.recognizeObject(object, missionImage);

            filePath = fileService.uploadMissionImage(
                    missionImage,
                    imageProcessor.drawODResultOnImage(missionImage, odr, object),
                    "mission");
        } else {
            throw new BaseException(Status.UNSUPPORTED_MEDIA_TYPE);
        }

        // 걸린 시간 계산
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("총 걸린 시간 : " + timeElapsed);

        return new BaseResponse<>(Status.MISSION_SUCCESS, new PostPerformMissionRes(filePath));
    }

    @Operation(summary = "표정 인식 미션")
    @PostMapping(value = "/face-recognition/{object}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<PostPerformMissionRes> recognizeFaceExpression(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @Parameter(required = true, description = "미션 수행 사진") @RequestPart MultipartFile missionImage,
            @Parameter(required = true, description = "탐지할 표정") @PathVariable String object
    ) throws Exception {
        System.out.println("\n[표정 인식 미션 api 시작]");
        long startTime = System.currentTimeMillis();

        String filePath;

        // 이미지 형식 검사 : jpg
        if (Objects.equals(missionImage.getContentType(), MediaType.IMAGE_JPEG_VALUE)) {
            FaceRecognitionRes frc = missionService.recognizeEmotion(object, missionImage);

            filePath = fileService.uploadMissionImage(
                    missionImage,
                    imageProcessor.drawODResultOnImage(missionImage, frc, object),
                    "mission");
        } else {
            throw new BaseException(Status.UNSUPPORTED_MEDIA_TYPE);
        }

        // 걸린 시간 계산
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("총 걸린 시간 : " + timeElapsed);

        return new BaseResponse<>(Status.MISSION_SUCCESS, new PostPerformMissionRes(filePath));
    }

    // TODO : 미션 수행이랑 합치기
    @Operation(summary = "미션 수행 기록 생성")
    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<GetMissionLogRes> postMissionLog(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @RequestBody @Valid PostMissionLogReq postMissionLogReq
    ) {
        missionService.createMissionLog(userId, postMissionLogReq);

        return new BaseResponse<>(Status.SUCCESS_CREATED);
    }

    // TODO : 달별 검색
    // TODO : 일별 검색
    @Operation(summary = "미션 수행 기록 가져오기")
    @GetMapping("/logs")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<GetMissionLogRes>> getMissionCompleteLogsByUserId(
            @Parameter(hidden = true) @AuthUser Integer userId
    ) {
        return new BaseResponse<>(Status.SUCCESS,
                missionProvider.getMissionCompleteLogsByUserId(userId));
    }
}
