package com.wakeUpTogetUp.togetUp.users.dto.request;


import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.users.LoginType;

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
                .username(username)
                .email(email)
                .loginType(LoginType.valueOf(loginType))
                .build();
    }


}
