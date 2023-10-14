package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Schema(description = "룸리스트 요청 api 응답")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomRes {

    @Schema( description = "룸 id", example = "1")
    private Integer roomId;

    @Schema( description = "룸이 가지고 있는 알람의 아이콘", example = "⏰")
    private String icon;

    @Schema( description = "룸 이름", example = "일찍 일어나는 새들의 방")
    private String name;

    @Schema( description = "룸이 가지고 있는 알람의 미션", example = "수저 찍기")
    private String mission;

}
