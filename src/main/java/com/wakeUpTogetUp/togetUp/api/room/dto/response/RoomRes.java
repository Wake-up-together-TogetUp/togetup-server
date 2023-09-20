package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomRes {

    private Integer roomId;
    private String icon;
    private String name;
    private String mission;

}
