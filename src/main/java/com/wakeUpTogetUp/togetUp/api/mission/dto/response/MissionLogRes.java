package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionLogRes {
    @Schema(description = "미션 수행 기록 id")
    private Integer id;

    @Schema(description = "알람 이름", example = "기상 알람")
    private String alarmName;

    @Schema(description = "미션 수행 사진")
    private String missionPicLink;

    @Schema(description = "생성 일자")
    private String createdAt;

    @Schema(description = "유저 id")
    private Integer userId;

    @Schema(description = "그룹 id")
    private Integer roomId;

    @Schema(description = "미션 id")
    private Integer missionId;
}
