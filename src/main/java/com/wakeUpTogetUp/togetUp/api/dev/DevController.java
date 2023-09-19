package com.wakeUpTogetUp.togetUp.api.dev;

import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "A-개발용")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/a-dev")
public class DevController {

    private final DevService devService;


    @Operation(summary = "유저 가져오기", description = "유저 아이디와 이름")
    @ResponseBody
    @GetMapping("/user")
    public BaseResponse<List<User>> user() {


        return new BaseResponse<>(Status.SUCCESS,devService.get());

    }

    @Operation(summary = "dev용 : accessToken 발금", description = "jwt 발급")
    @ResponseBody
    @GetMapping("/login/dev")
    public BaseResponse<String> join(@Parameter(description = "jwt 받고 싶은 유저의 ID") Integer userId) {

        String jwt = devService.devGetJwt(userId);

        return new BaseResponse<>(Status.SUCCESS,jwt);

    }



}
