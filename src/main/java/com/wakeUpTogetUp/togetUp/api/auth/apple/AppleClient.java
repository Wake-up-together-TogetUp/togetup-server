package com.wakeUpTogetUp.togetUp.api.auth.apple;


import com.wakeUpTogetUp.togetUp.api.auth.dto.request.AppleRevokeReq;
import com.wakeUpTogetUp.togetUp.api.auth.dto.request.AppleTokenReq;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.AppleTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "apple-public-key-client", url = "https://appleid.apple.com/auth")
public interface AppleClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKeys();

    @PostMapping(value = "/token",consumes = "application/x-www-form-urlencoded")
    AppleTokenRes findAppleToken(@RequestBody AppleTokenReq req);

    @PostMapping(value = "/revoke",consumes = "application/x-www-form-urlencoded")
    void revoke(AppleRevokeReq req);
}
