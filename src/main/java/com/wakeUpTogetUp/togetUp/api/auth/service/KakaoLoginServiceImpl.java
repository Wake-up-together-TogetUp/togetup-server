package com.wakeUpTogetUp.togetUp.api.auth.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.KakaoLoginRes;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.api.auth.kakao.KakaoUserApi;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("kakaoLogin")
public class KakaoLoginServiceImpl implements SocialLoginService {
    private final KakaoUserApi kakaoUserApi;


    @Override
    public LoginType getServiceName() {
        return LoginType.KAKAO;
    }

    @Override
    public SocialUserRes getUserInfo(String accessToken) {
        Map<String ,String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + accessToken);

        ResponseEntity<?> response  =null;
        try {
            response = kakaoUserApi.getUserInfo(headerMap);
        }catch (Exception e){
            throw new BaseException(Status.UNAUTHORIZED_KAKAO_TOKEN);
        }
        String jsonString = response.getBody().toString();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        KakaoLoginRes kakaoLoginRes = gson.fromJson(jsonString, KakaoLoginRes.class);
        KakaoLoginRes.KakaoLoginData kakaoLoginData = Optional.ofNullable(kakaoLoginRes.getKakao_account())
                .orElse(KakaoLoginRes.KakaoLoginData.builder().build());

        String name = Optional.ofNullable(kakaoLoginData.getProfile())
                .orElse(KakaoLoginRes.KakaoLoginData.KakaoProfile.builder().build())
                .getNickname();

        return SocialUserRes.builder()
                .id(kakaoLoginRes.getId())
                .name(name)
                .build();
    }
}
