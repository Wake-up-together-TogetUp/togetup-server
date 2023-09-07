package com.wakeUpTogetUp.togetUp.api.auth.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AppleTokenReq {
    private String client_id;
    private String client_secret;
    private String code;
    private String grant_type;
}
