package com.wakeUpTogetUp.togetUp.api.room.dto.request;


import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Schema(description = "그룹과 그룹 알람 생성")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomReq {

    @Schema(description = "룸 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "일찍 일어나는 새들의 방")
    @NotBlank(message = "룸 이름은 공백일 수 없습니다.")
    private String name;

    @Schema(description = "룸 설명", example = "설여대 소융이들의 방입니다.")
    private String intro;

    @NotBlank(message = "룸의 알람은 공백일 수 없습니다.")
    private PostAlarmReq postAlarmReq;
}
