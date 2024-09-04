package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomJoinRes {

  @Schema(description = "룸 id", example = "1")
  private Integer roomId;

  @Schema(description = "알람 id", example = "1")
  private Integer alarmId;

  public static RoomJoinRes of(Integer roomId, Integer alarmId) {

    return RoomJoinRes.builder()
        .roomId(roomId)
        .alarmId(alarmId)
        .build();
  }

}


