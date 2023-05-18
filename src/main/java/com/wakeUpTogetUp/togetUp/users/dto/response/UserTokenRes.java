package com.wakeUpTogetUp.togetUp.users.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenRes {
    Integer id;
    private String accessToken;
    private String tokenType;
}
