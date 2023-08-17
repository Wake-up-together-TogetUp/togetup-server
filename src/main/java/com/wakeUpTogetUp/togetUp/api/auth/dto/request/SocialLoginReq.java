package com.wakeUpTogetUp.togetUp.api.auth.dto.request;



import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class SocialLoginReq {

    @NotNull
    private String oauthAccessToken;
    @NotNull
    private LoginType loginType;


}

