package com.wakeUpTogetUp.togetUp.api.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppleTokenRes {
    @JsonProperty(value = "access_token")
    String accessToken;
    @JsonProperty(value = "expires_in")
    Integer expiresIn;
    @JsonProperty(value = "id_token")
    String idToken;
    @JsonProperty(value = "refresh_token")
    String refreshToken;
    @JsonProperty(value = "token_type")
    String tokenType;

}
