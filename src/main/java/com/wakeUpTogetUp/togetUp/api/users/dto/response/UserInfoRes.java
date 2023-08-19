package com.wakeUpTogetUp.togetUp.api.users.dto.response;


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

}
