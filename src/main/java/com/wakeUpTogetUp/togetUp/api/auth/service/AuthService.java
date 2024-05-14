package com.wakeUpTogetUp.togetUp.api.auth.service;

import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.AppleTokenRes;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.LoginRes;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.api.avatar.application.UserAvatarQueryService;
import com.wakeUpTogetUp.togetUp.api.users.UserService;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserStat;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/app/auth")
public class AuthService {

    private final List<SocialLoginService> loginServices;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserAvatarQueryService userAvatarQueryService;
    private final AppleLoginServiceImpl appleLoginService;

    @Transactional
    public LoginRes socialLogin(SocialLoginReq socialLoginReq) {


        SocialLoginService loginService = this.getLoginService(socialLoginReq.getLoginType());

        SocialUserRes socialUserRes = loginService.getUserInfo(
                socialLoginReq.getOauthAccessToken());
        log.info("socialUserResponse {} ", socialUserRes.toString());

        if (socialLoginReq.getLoginType().equals(LoginType.APPLE)) {
            socialUserRes.setName(socialLoginReq.getUserName());
        }

        User user = userService.getSignedUser(socialUserRes, socialLoginReq.getLoginType());

        String accessToken = jwtService.generateAccessToken(user.getId());

        return LoginRes.builder()
                .userId(user.getId())
                .userName(user.getName())
                .email(socialUserRes.getEmail())
                .accessToken(accessToken)
                .avatarId(userAvatarQueryService.getUserAvatarId(user.getId()))
                .userStat(UserStat.from(user))
                .build();
    }

    private SocialLoginService getLoginService(LoginType loginType) {
        for (SocialLoginService loginService : loginServices) {
            if (loginType.equals(loginService.getServiceName())) {
                log.info("login service name: {}", loginService.getServiceName());
                return loginService;
            }
        }
        //todo 수정
        return loginServices.get(0);
    }

    @Transactional
    public void disconnectApple(String authCode) throws IOException {

        AppleTokenRes appleToken = appleLoginService.getAppleToken(authCode);
        appleLoginService.revoke(appleToken.getAccessToken());
    }
}

