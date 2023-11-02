package com.wakeUpTogetUp.togetUp.api.auth;

import com.wakeUpTogetUp.togetUp.api.auth.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.auth.service.AuthService;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.LoginRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth(로그인)", description = "로그인 및 유저의 권한 인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인 api")
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<LoginRes> join(@RequestBody SocialLoginReq loginReq) {

        LoginRes loginRes = authService.socialLogin(loginReq);

        return new BaseResponse<>(Status.SUCCESS_CREATED, loginRes);

    }


}



