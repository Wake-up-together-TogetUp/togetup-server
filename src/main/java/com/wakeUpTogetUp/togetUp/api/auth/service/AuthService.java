package com.wakeUpTogetUp.togetUp.api.auth.service;

import com.wakeUpTogetUp.togetUp.api.auth.dto.response.LoginRes;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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



    @Transactional
    public LoginRes socialLogin(SocialLoginReq socialLoginReq)  {

        //각 소셜로그인에 맞는 서비스 가져오기
        SocialLoginService loginService = this.getLoginService(socialLoginReq.getLoginType());

        //소셜로그인 유저 정보 가져오기
        SocialUserRes socialUserRes = loginService.getUserInfo(socialLoginReq.getOauthAccessToken());
        log.info("socialUserResponse {} ", socialUserRes.toString());

        //저장된 유저 or 저장한유저의 id 가져오기
        Integer userId = this.getSignedUserId(socialUserRes,socialLoginReq.getLoginType());

        //accessToken 만들기
        String accessToken = jwtService.generateAccessToken(userId);

        return LoginRes.builder()
                .userId(userId)
                .useName("박건우")//socialUserRes.getName())
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
        return new LoginServiceImpl();
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
                        .build()
        );

        return user.getId();
    }





}

