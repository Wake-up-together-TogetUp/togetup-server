package com.wakeUpTogetUp.togetUp.users.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor

public class PatchUserReq {
    private String username;
  //  private String email;
    private String statusMessage;
}
//TODO 이메일하고 비밀번호 변경은 따로?
