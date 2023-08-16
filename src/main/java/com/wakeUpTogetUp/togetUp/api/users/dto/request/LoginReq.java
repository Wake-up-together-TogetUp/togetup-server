package com.wakeUpTogetUp.togetUp.api.users.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class LoginReq {
    private String email;
    private String password;
}
