package com.wakeUpTogetUp.togetUp.users.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {


    private Integer id;
    private String jwt;

}