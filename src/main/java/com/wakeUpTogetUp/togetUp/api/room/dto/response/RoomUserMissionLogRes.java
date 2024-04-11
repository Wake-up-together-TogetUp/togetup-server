package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


@Schema(description = "room의 멤버들의 미션로그 api 응답")
@Data
@AllArgsConstructor(staticName = "of")
public class RoomUserMissionLogRes {

    @Schema(description = "room name", example = "설여대 기상방")
    private String name;

    @Schema(description = "- 신입 병아리 \n - 눈을 반짝이는 곰돌이 \n - 깜찍한 토끼", example = "신입 병아리")
    private String theme;

    private List<UserLogData> userLogList;



}
