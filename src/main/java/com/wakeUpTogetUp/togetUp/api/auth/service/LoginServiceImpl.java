package com.wakeUpTogetUp.togetUp.api.auth.service;

import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Component
@Qualifier("defaultLoginService")
public class LoginServiceImpl implements SocialLoginService {
    @Override
    public LoginType getServiceName() {
        return LoginType.LOCAL;
    }

    @Override
    public SocialUserRes getUserInfo(String accessToken) {
        return null;
    }


}
