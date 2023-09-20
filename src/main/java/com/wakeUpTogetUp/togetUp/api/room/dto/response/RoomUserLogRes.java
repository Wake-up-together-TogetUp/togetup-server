package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import com.wakeUpTogetUp.togetUp.api.room.UserCompleteType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;


@Schema(description = "room 로그 response")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomUserLogRes {

    @Schema(description = "room name")
    private String name;
    private List<UserLogData> userLogList;

    @Builder
    @Data
    public static class UserLogData {
        @Schema(description = "유저 Id")
        private Integer userId;

        @Schema(description = "유저 이름")
        private String userName;

        @Schema(description = "나인지 다른 멤버인지 여부")
        private Boolean isMyLog;
        @Schema(description = "유저의 미션 수행 상태 (성공, 실패, 아직 수행 안함, 수행하는 날 아님)")
        private UserCompleteType userCompleteType;

        @Schema(description = "미션 수행 사진")
        private String missionPicLink;


    }





}
