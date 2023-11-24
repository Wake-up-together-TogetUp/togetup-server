package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(description = "룸정보 조회 요청 api 응답")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomInfoRes {

    @Schema(description = "룸 정보")
    private RoomData roomData;

    @Schema(description = "알람 정보")
    private AlarmData alarmData;

    @Builder
    @Data
    public static class RoomData {

        @Schema(description = "룸아이디", example = "1")
        private Integer id;

        @Schema(description = "룸이 가지고 있는 알람의 아이콘", example = "⏰")
        private String icon;

        @Schema(description = "room name", example = "설여대 기상방")
        private String name;

        @Schema(description = "room 소개", example = "소프트웨어융학학과 기상방입니다.")
        private String intro;

        @Schema(description = "room 개설일", example = "2020.02.03")
        private String createdAt;

        @Schema(description = "함께하는 인원수", example = "6")
        private Integer personnel;


    }

    @Builder
    @Data
    public static class AlarmData {

        @Schema(description = "미션의 한국말", example = "자동차")
        private String missionKr;

        @Schema(description = "알람 시간", example = "pm 13:00")
        private LocalTime alarmTime;

        @Schema(description = "그룹알람 울리는 날", example = "주중")
        private String alarmDay;


    }


}
