package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.users.LoginType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Data;

@Getter
@Data
@NoArgsConstructor
public class UserForm {
   private Integer id;
   private String password;
    private String username;
    private String email;
    private LoginType loginType;

    @Builder
    public UserForm(Integer id,String password, String username,String email,LoginType loginType) {
        this.id = id;
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

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public UserForm(String username, String email, LoginType loginType) {
        this.username = username;
        this.email = email;
        this.loginType=loginType;

    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .password(password)
                .username(username)
                .email(email)
                .loginType(loginType)
                .build();
    }
}