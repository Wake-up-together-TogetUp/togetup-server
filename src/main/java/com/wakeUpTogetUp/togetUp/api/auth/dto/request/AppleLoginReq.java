package com.wakeUpTogetUp.togetUp.api.auth.dto.request;



import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class AppleLoginReq {

    @NotNull
    private String idToken;
    @NotNull
    private LoginType loginType;

    private String userName;
}

