package com.wakeUpTogetUp.togetUp.api.auth.dto.response;

import com.wakeUpTogetUp.togetUp.api.users.vo.UserStat;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes {
    private Integer userId;
    private String userName;
    private String email;
    private String accessToken;
    private Integer avatarId;
    private UserStat userStat;
}
