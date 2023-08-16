package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.users.dto.request.LoginReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.PatchUserReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.UserReq;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.config.annotation.NoAuth;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserInfoRes;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserRes;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserTokenRes;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final UserRepository userRepository;


    //TODO 알림 받을지 여부 업데이트


}





