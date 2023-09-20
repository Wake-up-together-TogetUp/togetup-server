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
public class RoomUserLogReq {

    @Schema(description = "room id")
    Integer roomId;

    @Schema(description = "기록을 가져올 날의 LocalDateTime ")
    @NotBlank(message = "기록 시간은 공백일 수 없습니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime localDateTime;

    @Schema(description = "알람이 울리는 날인지 여부")
    @NotBlank(message = "알람이 울리는 날인지 여부는 공백일 수 없습니다.")
    Boolean isAlarmActive;



}
