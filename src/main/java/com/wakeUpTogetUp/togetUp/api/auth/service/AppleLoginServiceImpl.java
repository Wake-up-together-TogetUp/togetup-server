package com.wakeUpTogetUp.togetUp.api.auth.service;

import com.amazonaws.util.IOUtils;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.apple.AppleClaimsValidator;
import com.wakeUpTogetUp.togetUp.api.auth.apple.AppleClient;
import com.wakeUpTogetUp.togetUp.api.auth.apple.AppleJwtParser;
import com.wakeUpTogetUp.togetUp.api.auth.apple.ApplePublicKeys;
import com.wakeUpTogetUp.togetUp.api.auth.apple.PublicKeyGenerator;
import com.wakeUpTogetUp.togetUp.api.auth.dto.request.AppleRevokeReq;
import com.wakeUpTogetUp.togetUp.api.auth.dto.request.AppleTokenReq;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.AppleLoginRes;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.AppleTokenRes;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("appleLogin")
public class AppleLoginServiceImpl implements SocialLoginService {

    private final AppleJwtParser appleJwtParser;
    private final AppleClient appleClient;
    private final PublicKeyGenerator publicKeyGenerator;
    private final AppleClaimsValidator appleClaimsValidator;
    @Value("${oauth.apple.client-id}")
    private String clientId;
    @Value("${oauth.apple.iss}")
    private String iss;
    @Value("${oauth.apple.key-id}")
    private String keyId;

    @Override
    public LoginType getServiceName() {
        return LoginType.APPLE;
    }

    @Override
    public SocialUserRes getUserInfo(String accessToken) {

        Map<String, String> headers = appleJwtParser.parseHeaders(accessToken);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();

        PublicKey publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys);
        Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(accessToken, publicKey);

        validateClaims(claims);

        AppleLoginRes appleLoginRes = new AppleLoginRes(claims.getSubject(), claims.get("email", String.class));

        return SocialUserRes.builder()
                .id(appleLoginRes.getId())
                .email(appleLoginRes.getEmail())
                .build();
    }

    private void validateClaims(Claims claims) {
        if (!appleClaimsValidator.isValid(claims)) {
            throw new BaseException(Status.INVALID_APPLE_CLAIMS);

        }
    }

    public AppleTokenRes getAppleToken(String authorizationCode) throws IOException {

        AppleTokenReq appleTokenReq = AppleTokenReq.builder()
                .client_id(clientId)
                .client_secret(this.createClientSecret())
                .code(authorizationCode)
                .grant_type("authorization_code")
                .build();

        return appleClient.findAppleToken(appleTokenReq);
    }

    // TODO: deprecated된 메소드 수정하기
    public String createClientSecret() throws IOException {

        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setHeaderParam("kid", keyId)
                .setHeaderParam("alg", "ES256")
                .setIssuer(iss)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject(clientId)
                .signWith(SignatureAlgorithm.ES256, this.getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("AuthKey_M992KTZK9V.p8");

        if (inputStream == null) {
            throw new BaseException(Status.FILE_NOT_FOUND);
        }

        byte[] privateKeyBytes = IOUtils.toByteArray(inputStream);
        Reader pemReader = new StringReader(new String(privateKeyBytes));
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        return converter.getPrivateKey(object);
    }


    public void revoke(String accessToken) throws IOException {
        try {
            AppleRevokeReq appleRevokeReq = AppleRevokeReq.builder()
                    .client_id(clientId)
                    .client_secret(this.createClientSecret())
                    .token(accessToken)
                    .token_type_hint("access_token")
                    .build();
            appleClient.revoke(appleRevokeReq);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Apple Revoke Error");
        }
    }


}
