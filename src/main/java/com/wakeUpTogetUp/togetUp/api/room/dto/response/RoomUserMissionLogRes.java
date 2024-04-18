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

    @Schema(description = "- SENIOR_CHICK \n - ASTRONAUT_BEAR \n - LOVELY_BUNNY \n - RAINY_DAY_PUPPY \n - PHILOSOPHER_RACCOON", example = "GLUTTON_PANDA")
    private String theme;

    private List<UserLogData> userLogList;



}
