package com.wakeUpTogetUp.togetUp.login.oauth.info;

import com.wakeUpTogetUp.togetUp.login.oauth.entity.ProviderType;

import com.wakeUpTogetUp.togetUp.login.oauth.info.impl.GoogleOAuth2UserInfo;


import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
