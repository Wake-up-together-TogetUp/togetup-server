package com.wakeUpTogetUp.togetUp.chat.dto.request;



import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostChatRoomReq {

    private String name;
    private String intro;
    private String password;

}

