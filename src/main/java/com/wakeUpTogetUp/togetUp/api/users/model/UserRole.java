package com.wakeUpTogetUp.togetUp.api.users.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    USER,//("ROLE_USER", "일반 사용자 권한"),
    ADMIN,//("ROLE_ADMIN", "관리자 권한"),
    GUEST//("GUEST", "게스트 권한");
}
