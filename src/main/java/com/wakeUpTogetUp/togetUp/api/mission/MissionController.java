package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.MissionCompleteReq;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionWithObjectListRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionCompleteRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionPerfomRes;
import com.wakeUpTogetUp.togetUp.api.mission.service.MissionProvider;
import com.wakeUpTogetUp.togetUp.api.mission.service.MissionService;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.annotation.validator.ImageFile;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "미션(Mission)")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/mission")
public class MissionController {

    private final MissionFacade missionFacade;
    private final MissionProvider missionProvider;
    private final MissionService missionService;

    @Operation(summary = "미션 목록 가져오기")
    @GetMapping("/{missionId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = GetMissionWithObjectListRes.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 미션 입니다.")})
    BaseResponse<GetMissionWithObjectListRes> getObjectDetectionMissions(
            @Parameter(required = true, description = "- 객체인식 : 2\n- 표정인식 : 3") @PathVariable(value = "missionId") int missionId
    ) {
        return new BaseResponse<>(Status.SUCCESS, missionProvider.getMission(missionId));
    }

    @Operation(summary = "미션 수행 결과 불러오기",
            description = "미션 이름을 기반으로 미션을 수행합니다.\n"
                    + "- 사용 가능 미션 이름: `direct-registration`/`object-detection`/`expression-recognition`\n"
                    + "- 직접 촬영 미션(direct-registration)의 경우 object 필드가 필수적이지 않습니다.")
    @PostMapping(value = "/{missionName}/result", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "미션을 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = MissionPerfomRes.class))),
            @ApiResponse(responseCode = "200", description = "탐지된 객체가 없습니다."),
            @ApiResponse(responseCode = "200", description = "미션을 성공하지 못했습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러입니다. 제보 부탁드립니다.")})
    public BaseResponse<MissionPerfomRes> recognizeObject(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @Parameter(required = true, description = "미션 수행 사진") @RequestPart @ImageFile MultipartFile missionImage,
            @Parameter(description = "미션 이름") @PathVariable String missionName,
            @Parameter(description = "탐지할 미션 객체") @RequestParam(required = false) String object
    ) {
        MissionPerfomRes response = missionFacade.performMission(missionImage, missionName, object);
        return new BaseResponse<>(Status.MISSION_SUCCESS, response);
    }

    @Operation(summary = "미션 완료 - 수행 기록 생성 및 경험치, 레벨 정산 및 경우에 따라 아바타 해금")
    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "생성 되었습니다.",
                    content = @Content(schema = @Schema(implementation = MissionCompleteRes.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 알람 입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")
    })
    public BaseResponse<MissionCompleteRes> processMissionCompletion(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @RequestBody @Valid MissionCompleteReq missionCompleteReq
    ) {
        return new BaseResponse<>(Status.SUCCESS_CREATED,
                missionService.afterMissionComplete(userId, missionCompleteReq));
    }

    // TODO : 달별 검색
    // TODO : 일별 검색
    @Operation(summary = "미션 수행 기록 가져오기")
    @GetMapping("/logs")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(
            responseCode = "200",
            description = "요청에 성공하였습니다.",
            content = @Content(schema = @Schema(implementation = GetMissionLogRes.class)))
    public BaseResponse<List<GetMissionLogRes>> getMissionCompleteLogsByUserId(
            @Parameter(hidden = true) @AuthUser Integer userId
    ) {
        return new BaseResponse<>(Status.SUCCESS,
                missionProvider.getMissionCompleteLogsByUserId(userId));
    }
}
