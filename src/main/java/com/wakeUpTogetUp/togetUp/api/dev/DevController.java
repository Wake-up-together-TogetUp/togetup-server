package com.wakeUpTogetUp.togetUp.api.dev;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
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
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "A-개발용")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/a-dev")
public class DevController {

    private final DevService devService;

    @Operation(summary = "dev: 유저 가져오기", description = "유저 아이디와 이름")
    @ResponseBody
    @GetMapping("/users")
    public BaseResponse<List<User>> user() {
        return new BaseResponse<>(Status.SUCCESS, devService.getUsers());
    }

    @Operation(summary = "dev: accessToken 발급", description = "jwt 발급")
    @ResponseBody
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
}
