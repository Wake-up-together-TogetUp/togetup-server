package com.wakeUpTogetUp.togetUp.api.room.dto.request;


import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;



@Schema(description = "그룹과 그룹 알람 생성")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomReq {

    @Schema( example = "일찍 일어나는 새들의 방")
    private String name;
    @Schema( example = "설여대 소융이들의 방입니다.")
    private String intro;
    @Schema(example = "{\n" +
            "    \"missionId\": 1,\n" +
            "    \"roomId\": 0,\n" +
            "    \"name\": \"test\",\n" +
            "    \"icon\": \"⏰\",\n" +
            "    \"isVibrate\": true,\n" +
            "    \"snoozeInterval\": 5,\n" +
            "    \"snoozeCnt\": 3,\n" +
            "    \"alarmTime\": \"06:00\",\n" +
            "    \"monday\": false,\n" +
            "    \"tuesday\": false,\n" +
            "    \"wednesday\": false,\n" +
            "    \"thursday\": false,\n" +
            "    \"friday\": false,\n" +
            "    \"saturday\": false,\n" +
            "    \"sunday\": false,\n" +
            "    \"isActivated\": true\n" +
            "  }")
    private PostAlarmReq postAlarmReq;


}
