package com.wakeUpTogetUp.togetUp.api.dev;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.dev.dto.response.AppStoreUrlRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.MissionCompleteReq;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionCompleteRes;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserStat;
import com.wakeUpTogetUp.togetUp.common.Status;
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
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "A-개발용")
@RestController
@RequestMapping("/app/a-dev")
@RequiredArgsConstructor
@Validated
public class DevController {

    private final DevService devService;
    private final AppVersionProvider appVersionProvider;

    @Operation(summary = "dev: 유저 가져오기", description = "유저 아이디와 이름")
    @GetMapping("/users")
    public BaseResponse<List<User>> user() {
        return new BaseResponse<>(Status.SUCCESS, devService.getUsers());
    }

    @Operation(summary = "dev: accessToken 발급", description = "jwt 발급")
    @GetMapping("/login/dev")
    public BaseResponse<String> join(@Parameter(description = "jwt 받고 싶은 유저의 ID") Integer userId) {
        String jwt = devService.devGetJwt(userId);

        return new BaseResponse<>(Status.SUCCESS, jwt);
    }

    @Operation(summary = "dev: 레벨업 응답 api")
    @PostMapping("/mission/complete")
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
        User user = new User(userId,
                "1234",
                "이예원",
                "example@gmail.com",
                LoginType.KAKAO,
                30,
                3);

        MissionCompleteRes response = MissionCompleteRes.builder()
                .userStat(UserStat.from(user))
                .isUserLevelUp(true)
                .isAvatarUnlockAvailable(true)
                .build();

        return new BaseResponse<>(Status.SUCCESS_CREATED, response);
    }

    @Operation(summary = "dev: app version 체크 api")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "요청에 성공했습니다.",
                    content = @Content(schema = @Schema(implementation = AppStoreUrlRes.class))),
            @ApiResponse(responseCode = "400", description = "데이터 내의 최신 버전보다 높은 버전입니다."),
            @ApiResponse(responseCode = "500", description = "앱 최신버전 정보를 불러오는데 실패했습니다.")
    })
    @GetMapping("/version/check")
    public BaseResponse<AppStoreUrlRes> checkAppVersion(
            @Parameter @RequestParam
            @Pattern(regexp = "^\\d+\\.\\d+\\.\\d+$", message = "앱 버전 포맷이 유효하지 않습니다.")
            String currentAppVersion
    ) {
        AppStoreUrlRes response = appVersionProvider.getAppVersionCheckResult(currentAppVersion);

        return new BaseResponse<>(Status.SUCCESS, response);
    }
}

