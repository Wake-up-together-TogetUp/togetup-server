package com.wakeUpTogetUp.togetUp.api.auth;

import com.wakeUpTogetUp.togetUp.api.auth.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.auth.service.AuthService;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.LoginRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequiredArgsConstructor
@RequestMapping("/app/auth")
public class AuthController {
    private final AuthService authService;

    /**
     *
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<LoginRes> join(@RequestBody SocialLoginReq loginReq) {

           LoginRes loginRes = authService.socialLogin(loginReq);

            return new BaseResponse<>(Status.SUCCESS,loginRes);

    }



}



