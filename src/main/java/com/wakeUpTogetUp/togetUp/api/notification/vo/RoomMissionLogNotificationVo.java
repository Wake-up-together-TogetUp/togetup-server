package com.wakeUpTogetUp.togetUp.api.notification.vo;


import com.wakeUpTogetUp.togetUp.api.notification.DataKeyType;
import com.wakeUpTogetUp.togetUp.api.notification.DataValueType;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.model.User;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomMissionLogNotificationVo extends NotificationSendVo {
    private static final String title = "%s님이 미션을 완료했어요!";
    private static final String body = "%s 그룹";

    public RoomMissionLogNotificationVo(User user, Room room, List<FcmToken> fcmTokens) {
        super(fcmTokens, String.format(title, user.getName()), String.format(body, room.getName()), createDataMap(room.getId()));

    }

    private static Map<String, String> createDataMap(Integer roomId) {

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put(DataKeyType.LINK.getKey(), DataValueType.ROOM.toString());
        dataMap.put(DataKeyType.ROOM_ID.getKey(), String.valueOf(roomId));

        return dataMap;
    }
}
