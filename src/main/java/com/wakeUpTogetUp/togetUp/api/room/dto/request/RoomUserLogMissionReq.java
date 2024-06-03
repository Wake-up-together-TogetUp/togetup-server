package com.wakeUpTogetUp.togetUp.api.room.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@ToString
@Schema(description = "room 로그 요청")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomUserLogMissionReq {

    @Schema(description = "룸 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotBlank(message = "룸 id는 공백일 수 없습니다.")
    Integer roomId;

    @Schema(description = "기록을 가져올 날의 LocalDateTime ", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-09-20 11:55:38")
    @NotBlank(message = "기록 시간은 공백일 수 없습니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime localDateTime;

    @Schema(description = "알람이 울리는 날인지 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotBlank(message = "알람이 울리는 날인지 여부는 공백일 수 없습니다.")
    Boolean isAlarmActive;


}
