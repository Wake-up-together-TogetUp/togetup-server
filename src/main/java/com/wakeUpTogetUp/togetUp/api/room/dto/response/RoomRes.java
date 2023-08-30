package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRes {

    private Integer roomId;
    private String roomIcon;
    private String name;
    private String mission;
    private String recentlyMissionLog;
    //private boolean isRead;
}
