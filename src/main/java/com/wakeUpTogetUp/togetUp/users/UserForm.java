package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import lombok.Data;

@Getter
@Data
@NoArgsConstructor
public class UserForm {
   private Integer id;
   private String password;
    private String username;
    private String email;

    @Builder
    public UserForm(Integer id,String password, String username,String email) {
        this.id = id;
        this.password=password;
        this.username = username;
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .password(password)
                .username(username)
                .email(email)
                .build();
    }
}