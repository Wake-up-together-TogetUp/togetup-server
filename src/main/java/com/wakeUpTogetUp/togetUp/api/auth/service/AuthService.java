package com.wakeUpTogetUp.togetUp.api.auth.service;

import static com.wakeUpTogetUp.togetUp.common.Constant.DEFAULT_AVATAR_ID;

import com.wakeUpTogetUp.togetUp.api.auth.dto.response.AppleTokenRes;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.LoginRes;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.api.avatar.AvatarRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import com.wakeUpTogetUp.togetUp.api.users.UserAvatarPurchaseLogRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserAvatarRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatarPurchaseLog;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/app/auth")
public class AuthService {
    private final List<SocialLoginService> loginServices;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;
    private final UserAvatarPurchaseLogRepository userAvatarPurchaseLogRepository;
    private final AppleLoginServiceImpl appleLoginService;


    @Transactional
    public LoginRes socialLogin(SocialLoginReq socialLoginReq)  {

        //각 소셜로그인에 맞는 서비스 가져오기
        SocialLoginService loginService = this.getLoginService(socialLoginReq.getLoginType());

        //소셜로그인 유저 정보 가져오기
        SocialUserRes socialUserRes = loginService.getUserInfo(socialLoginReq.getOauthAccessToken());
        log.info("socialUserResponse {} ", socialUserRes.toString());


        if(socialLoginReq.getLoginType().equals(LoginType.APPLE))
            socialUserRes.setName(socialLoginReq.getUserName());

        //저장된 유저 or 저장한유저의 id 가져오기
        Integer userId = this.getSignedUserId(socialUserRes,socialLoginReq.getLoginType());

        //accessToken 만들기
        String accessToken = jwtService.generateAccessToken(userId);

        return LoginRes.builder()
                .userId(userId)
                .userName(socialUserRes.getName())
                .email(socialUserRes.getEmail())
                .accessToken(accessToken)
                .build();
    }
    private SocialLoginService getLoginService(LoginType loginType){
        for (SocialLoginService loginService: loginServices) {
            if (loginType.equals(loginService.getServiceName())) {
                log.info("login service name: {}", loginService.getServiceName());
                return loginService;
            }
        }
        //todo 수정
        return loginServices.get(0);
    }
    private Integer getSignedUserId(SocialUserRes socialUserRes,LoginType loginType ) {
        User savedUser =userRepository.findBySocialId(socialUserRes.getId()).orElse(null);
        //유저가 있으면 아이디 반환
        if(Objects.nonNull(savedUser))
            return savedUser.getId();

        // 유저 저장
        User user = userRepository.save(
                User.builder()
                        .socialId(socialUserRes.getId())
                        .loginType(loginType)
                        .name(socialUserRes.getName())
                        .email(socialUserRes.getEmail())
                        .build()
        );

        // 기본 아바타 가져오기
        Avatar defaultAvatar = avatarRepository.findById(DEFAULT_AVATAR_ID)
                .orElseThrow(() -> new BaseException(Status.AVATAR_NOT_FOUND));

        // 기본 아바타 구매 내역 row 생성
        UserAvatarPurchaseLog userAvatarPurchaseLog = UserAvatarPurchaseLog.builder()
                .user(user)
                .avatar(defaultAvatar)
                .build();
        userAvatarPurchaseLogRepository.save(userAvatarPurchaseLog);

        // 기본 아바타 설정 row 생성
        UserAvatar userAvatar = UserAvatar.builder()
                .user(user)
                .avatar(defaultAvatar)
                .build();
        userAvatarRepository.save(userAvatar);

        return user.getId();
    }


    @Transactional
    public void disconnectApple(String authCode) throws IOException {


        AppleTokenRes appleToken = appleLoginService.getAppleToken(authCode);
        appleLoginService.revoke(appleToken.getAccessToken());
    }


}

