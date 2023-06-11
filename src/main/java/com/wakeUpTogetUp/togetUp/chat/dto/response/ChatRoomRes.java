package com.wakeUpTogetUp.togetUp.chat.dto.response;

import com.wakeUpTogetUp.togetUp.file.dto.response.PostFileRes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRes {


    private String roomId;
    private String name;
    private String intro;
    private PostFileRes postFileRes;
    private String password;
    private int state;
}
