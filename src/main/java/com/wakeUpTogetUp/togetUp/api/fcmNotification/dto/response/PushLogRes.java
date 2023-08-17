package com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PushLogRes {
    private Integer receiverId;
    private String title;
    private String message;
    private String sendAt;
}
