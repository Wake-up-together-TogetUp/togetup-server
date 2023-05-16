package com.wakeUpTogetUp.togetUp.users.dto.response;


import com.wakeUpTogetUp.togetUp.users.LoginType;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRes {
    private Integer id;
    private String userName;
    private String statusMessage;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private LoginType loginType ;
}
