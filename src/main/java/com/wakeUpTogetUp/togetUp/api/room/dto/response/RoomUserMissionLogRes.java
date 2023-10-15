package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import com.wakeUpTogetUp.togetUp.api.avatar.AvatarTheme;
import com.wakeUpTogetUp.togetUp.api.room.UserCompleteType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;


@Schema(description = "room의 멤버들의 미션로그 api 응답")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomUserMissionLogRes {

    @Schema(description = "room name",example = "설여대 기상방")
    private String name;

    @Schema(description = "- 신입 병아리 \n - 눈을 반짝이는 곰돌이 \n - 깜찍한 토끼" ,example = "신입 병아리")
    private String theme;

    private List<UserLogData> userLogList;

    @Builder
    @Data
    public static class UserLogData {
        @Schema(description = "유저 Id",example = "1")
        private Integer userId;

        @Schema(description = "유저 이름",example = "조혜온")
        private String userName;

        @Schema(description = "나인지 다른 멤버인지 여부",example = "true")
        private Boolean isMyLog;

        @Schema(description = "유저의 미션 수행 상태 (성공 SUCCESS, 실패 FAIL, 아직 수행 안함 WAITING, 수행하는 날 아님 NOT_MISSION)",example = "WAITING")
        private UserCompleteType userCompleteType;

        @Schema(description = "미션 수행 사진",example = "http://~~~")
        private String missionPicLink;


    }





}
