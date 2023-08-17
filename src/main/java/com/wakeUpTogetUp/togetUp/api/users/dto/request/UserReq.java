package com.wakeUpTogetUp.togetUp.api.users.dto.request;

import com.wakeUpTogetUp.togetUp.api.users.model.User;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
public class UserReq {
  // private Integer id;
   private String password;
    private String username;
    private String email;
    private String loginType;

    @Builder
    public UserReq(Integer id, String password, String username, String email, String loginType) {
      //  this.id = id;
        this.password=password;
        this.username = username;
        this.email = email;
        this.loginType=loginType;
    }

//    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
//    public User(String username, String password, String email, Role role) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//    }

//    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
//    public UserReq(String username, String email, LoginType loginType) {
//        this.username = username;
//        this.email = email;
//        this.loginType=loginType;
//
//    }

    public User toEntity(){
        return User.builder()
             //   .id(id)
                .password(password)
                .name(username)
                .build();
    }
}