package com.wakeUpTogetUp.togetUp.api.auth.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes {

    private Integer userId;
    private String  useName;
    private String  accessToken;

}
