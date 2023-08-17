package com.wakeUpTogetUp.togetUp.api.users.dto.request;


import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.users.model.User;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor

public class SocialLoginReq {

    private String username;
    private String email;
    private String loginType;

    public User toEntity(){
        return User.builder()
                .name(username)
                .loginType(LoginType.valueOf(loginType))
                .build();
    }


}
