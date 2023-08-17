package com.wakeUpTogetUp.togetUp.api.auth.service;


import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import org.springframework.stereotype.Service;

@Service
public interface SocialLoginService {
    LoginType getServiceName();
    SocialUserRes getUserInfo(String accessToken);
}
