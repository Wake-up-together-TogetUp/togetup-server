package com.wakeUpTogetUp.togetUp.api.auth.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.apple.*;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.AppleLoginRes;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("appleLogin")
public class AppleLoginServiceImpl implements SocialLoginService {

    private final AppleJwtParser appleJwtParser;
    private final AppleClient appleClient;
    private final PublicKeyGenerator publicKeyGenerator;
    private final AppleClaimsValidator appleClaimsValidator;
    @Override
    public LoginType getServiceName() {
        return LoginType.APPLE;
    }

    @Override
    public SocialUserRes getUserInfo(String accessToken) {

        Map<String, String> headers = appleJwtParser.parseHeaders(accessToken);//안되면 identyfi 토큰으로 바꾸기
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();

        PublicKey publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys);
        Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(accessToken, publicKey);

        validateClaims(claims);

        AppleLoginRes appleLoginRes = new AppleLoginRes(claims.getSubject());

        return SocialUserRes.builder()
                .id(appleLoginRes.getId())
                .build();
    }

    private void validateClaims(Claims claims) {
        if (!appleClaimsValidator.isValid(claims)) {
            throw  new BaseException(Status.Invalid_APPLE_Token);

        }
    }
}
