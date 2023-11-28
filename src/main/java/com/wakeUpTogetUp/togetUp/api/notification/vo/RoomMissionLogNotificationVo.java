package com.wakeUpTogetUp.togetUp.api.notification.vo;


import com.wakeUpTogetUp.togetUp.api.notification.DataKeyType;
import com.wakeUpTogetUp.togetUp.api.notification.DataValueType;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.model.User;

import java.util.List;
import java.util.Map;

public class RoomMissionLogNotificationVo extends NotificationSendVo {
    private static final String title = "%s님이 미션을 완료했어요!";
    private static final String body = "%s 그룹";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), DataValueType.HOME.toString());
    public RoomMissionLogNotificationVo(Room room, User user, List<FcmToken> fcmTokens ,Map<String,Integer> dataMap) {
        super(fcmTokens, String.format(title, user.getName()), String.format(body, room.getName()),"category" ,Map.of());

    }
}
