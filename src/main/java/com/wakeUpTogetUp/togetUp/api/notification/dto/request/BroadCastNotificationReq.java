package com.wakeUpTogetUp.togetUp.api.notification.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BroadCastNotificationReq {
    @NotNull
    private String title;

    @NotNull
    private String body;
}
