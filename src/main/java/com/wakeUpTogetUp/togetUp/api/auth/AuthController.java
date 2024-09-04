package com.wakeUpTogetUp.togetUp.api.auth;

import com.wakeUpTogetUp.togetUp.api.auth.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.LoginRes;
import com.wakeUpTogetUp.togetUp.api.auth.service.AuthService;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "로그인 및 유저의 권한 인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 되었습니다."),
            @ApiResponse(responseCode = "500", description = "유저의 대표 아바타 정보를 가져오는데 실패했습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 애플 엑세스토큰입니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 카카오 엑세스토큰입니다."),
            @ApiResponse(responseCode = "400", description = "Apple OAuth Identity Token 형식이 올바르지 않습니다."),
            @ApiResponse(responseCode = "400", description = "Apple OAuth Claims 값이 올바르지 않습니다.")
    })
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<LoginRes> join(@RequestBody SocialLoginReq loginReq) {

        LoginRes loginRes = authService.socialLogin(loginReq);

        return new BaseResponse<>(Status.SUCCESS_CREATED, loginRes);

    }


}



