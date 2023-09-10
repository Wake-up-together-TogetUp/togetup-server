package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRes {

    private Integer roomId; //룸
    private String roomIcon; //알람
    private String name;  //룸
   // private String mission; //알람
 //   private String recentlyMissionLog;
    //private boolean isRead;
}
