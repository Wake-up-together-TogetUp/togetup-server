package com.wakeUpTogetUp.togetUp.api.notification.dto.response;

import com.wakeUpTogetUp.togetUp.api.notification.entity.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Schema(description = "notification list get api 응답 ")
@Getter
@Builder
public class NotificationListRes {

    private List<NotificationRes> notificationListRes;


    @Getter
    @Builder
    public static class NotificationRes {

        @Schema(description = "notification id", example = "1")
        private Integer id;

        @Schema(description = "제목", example = "000님이 미션을 완료했어요!")
        private String title;

        @Schema(description = "내용", example = "일찍 그룹")
        private String body;

        @Schema(description = "fcm 메시지에서 오는 것과 똑같은 data", example = "{\"link\":\"ROOM\",\"roomId\":\"13\"}")
        private Map<String, String> dataMap;

        @Schema(description = "읽었는지 여부", example = "true")
        private Boolean isRead;

        public static NotificationRes from(Notification notification) {
            return NotificationRes.builder()
                    .id(notification.getId())
                    .title(notification.getTitle())
                    .body(notification.getBody())
                    .dataMap(notification.getDataMap())
                    .isRead(notification.getIsRead())
                    .build();
        }

    }

    public static NotificationListRes from(List<Notification> notifications) {
        List<NotificationRes> notificationResList = notifications.stream()
                .map(NotificationRes::from)
                .collect(Collectors.toList());

        return NotificationListRes.builder()
                .notificationListRes(notificationResList)
                .build();
    }
}