package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.file.FileService;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.MissionLogCreateReq;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionWithObjectListRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionLogCreateRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionPerfomRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.ImageProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "미션(Mission)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/mission")
public class MissionController {
    private final MissionProvider missionProvider;
    private final MissionService missionService;
    private final FileService fileService;
    private final ImageProcessor imageProcessor;

    private final String tempImageUrl = "https://togetup.s3.ap-northeast-2.amazonaws.com/mission/2023/10/02/30c6e2f0-9766-4879-b08c-b6d53367ce81.missionImage";

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
    public BaseResponse<MissionPerfomRes> recognizeObject(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @Parameter(required = true, description = "미션 수행 사진") @RequestPart MultipartFile missionImage,
            @Parameter(required = true, description = "탐지할 객체") @PathVariable String object
    ) throws Exception {
//        System.out.println("\n[객체 탐지 미션 api 시작]");
//        long startTime = System.currentTimeMillis();
//
//        String filePath;
//
//        // 이미지 형식 검사 : jpg
//        if (Objects.equals(missionImage.getContentType(), MediaType.IMAGE_JPEG_VALUE)) {
//            ObjectDetectionRes odr = missionService.recognizeObject(object, missionImage);
//
//            filePath = fileService.uploadMissionImage(
//                    missionImage,
//                    imageProcessor.drawODResultOnImage(missionImage, odr, object),
//                    "mission");
//        } else {
//            throw new BaseException(Status.UNSUPPORTED_MEDIA_TYPE);
//        }
//
//        // 걸린 시간 계산
//        long endTime = System.currentTimeMillis();
//        long timeElapsed = endTime - startTime;
//        System.out.println("총 걸린 시간 : " + timeElapsed);

//        return new BaseResponse<>(Status.MISSION_SUCCESS, new MissionPerfomRes(filePath));
        return new BaseResponse<>(Status.MISSION_SUCCESS, new MissionPerfomRes(tempImageUrl));
    }

    @Operation(summary = "표정 인식 미션")
    @PostMapping(value = "/face-recognition/{object}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<MissionPerfomRes> recognizeFaceExpression(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @Parameter(required = true, description = "미션 수행 사진") @RequestPart MultipartFile missionImage,
            @Parameter(required = true, description = "탐지할 표정") @PathVariable String object
    ) throws Exception {
//        System.out.println("\n[표정 인식 미션 api 시작]");
//        long startTime = System.currentTimeMillis();
//
//        String filePath;
//
//        // 이미지 형식 검사 : jpg
//        if (Objects.equals(missionImage.getContentType(), MediaType.IMAGE_JPEG_VALUE)) {
//            FaceRecognitionRes frc = missionService.recognizeEmotion(object, missionImage);
//
//            filePath = fileService.uploadMissionImage(
//                    missionImage,
//                    imageProcessor.drawODResultOnImage(missionImage, frc, object),
//                    "mission");
//        } else {
//            throw new BaseException(Status.UNSUPPORTED_MEDIA_TYPE);
//        }
//
//        // 걸린 시간 계산
//        long endTime = System.currentTimeMillis();
//        long timeElapsed = endTime - startTime;
//        System.out.println("총 걸린 시간 : " + timeElapsed);

//        return new BaseResponse<>(Status.MISSION_SUCCESS, new MissionPerfomRes(filePath));
        return new BaseResponse<>(Status.MISSION_SUCCESS, new MissionPerfomRes(tempImageUrl));
    }

    @Operation(summary = "미션 수행 기록 생성 및 경험치, 레벨, 포인트 정산")
    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<MissionLogCreateRes> postMissionLog(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @RequestBody @Valid MissionLogCreateReq missionLogCreateReq
    ) {
        return new BaseResponse<>(Status.SUCCESS_CREATED,
                new MissionLogCreateRes(
                        missionService.afterMissionComplete(userId, missionLogCreateReq)));
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
