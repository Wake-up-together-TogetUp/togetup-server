package com.wakeUpTogetUp.togetUp.api.auth.dto.request;



import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "소셜로그인 요펑")
@Setter
@Getter
@NoArgsConstructor
public class SocialLoginReq {

    @NotNull
    @Schema( description = "로그인 플랫폼 엑세스 토큰",requiredMode = Schema.RequiredMode.REQUIRED ,example = "asjdfadjkfasdfafafadsfdfaf")
    private String oauthAccessToken;

    @NotNull
    @Schema( description = "로그인 타입",requiredMode = Schema.RequiredMode.REQUIRED ,example = "KAKAO")
    private LoginType loginType;

    @Schema( description = "유저이름",requiredMode = Schema.RequiredMode.NOT_REQUIRED ,example = "조혜온")
    private String userName;


}

