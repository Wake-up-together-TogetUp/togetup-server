package com.wakeUpTogetUp.togetUp.api.room.dto.request;


import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.request.AlarmCreateReq;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "그룹과 그룹 알람 생성")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomReq {

  @Schema(description = "룸 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "일찍 일어나자")
  @NotBlank(message = "룸 이름은 공백일 수 없습니다.")
  @Size(max = 10, message = "룸 이름은 10이하여야 합니다.")
  private String name;

  @Schema(description = "룸 설명", example = "설여대 소융이들의 방입니다.")
  @Size(max = 30, message = "룸 설명은 30글자 이하여야 합니다.")
  private String intro;

  @NotBlank(message = "알람은 공백일 수 없습니다.")
  private AlarmCreateReq alarmCreateReq;
}
