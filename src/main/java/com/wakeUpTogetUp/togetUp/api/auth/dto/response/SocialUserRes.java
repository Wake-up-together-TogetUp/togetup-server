package com.wakeUpTogetUp.togetUp.api.auth.dto.response;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class SocialUserRes {

    private String id;
    private String name;
    private String email;
}
