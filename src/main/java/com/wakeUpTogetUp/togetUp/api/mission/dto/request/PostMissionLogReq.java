package com.wakeUpTogetUp.togetUp.api.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "미션 로그 생성 request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMissionLogReq {
    @NotBlank(message = "알람 이름은 공백일 수 없습니다.")
    @Schema(description = "알람 이름", example = "기상 알람")
    private String alarmName;

    @NotBlank(message = "미션 수행 사진은 공백일 수 없습니다.")
    @Schema(description = "미션 수행 사진")
    private String missionPicLink;

    @Schema(description = "그룹 id")
    private Integer roomId;

    @NotNull(message = "미션 id는 null일 수 없습니다.")
    @Schema(description = "미션 id")
    private Integer missionId;
}
