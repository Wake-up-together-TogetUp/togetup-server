package com.wakeUpTogetUp.togetUp.fcmNotification.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UnsubscribeReq {
    private List<String> userTokenList;
    private String topic;
}
