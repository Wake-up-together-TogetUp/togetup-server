package com.wakeUpTogetUp.togetUp.api.auth.kakao;

import com.wakeUpTogetUp.togetUp.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(value = "kakaoUser", url="https://kapi.kakao.com", configuration = {FeignConfig.class})
public interface KakaoUserApi {
    @GetMapping("/v2/user/me")
    ResponseEntity<String> getUserInfo(@RequestHeader Map<String, String> header);

}