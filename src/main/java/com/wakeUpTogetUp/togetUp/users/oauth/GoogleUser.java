package com.wakeUpTogetUp.togetUp.users.oauth;

import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.users.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


//구글(서드파티)로 액세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
@AllArgsConstructor
@Getter
@Setter
public class GoogleUser {
    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String givenName;
    public String familyName;
    public String picture;
    public String locale;

    public User toEntity() {
                return User.builder()
                .username(name)
                .email(email)
                .loginType(LoginType.GOOGLE)
                .build();
    }
               //     .id(id)
    }

//    public GoogleUser toEntity(){
//        return User.builder()
//                .username(name)
//                .email(email)
//                .loginType(LoginType.GOOGLE)
//                .build();
//    }
//               //     .id(id)
//}
