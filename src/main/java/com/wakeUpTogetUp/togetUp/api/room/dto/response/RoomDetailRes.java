package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "룸정보 조회 요청 api 응답")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDetailRes {

    @Schema(description = "룸 정보")
    private RoomData roomData;

    @Schema(description = "알람 정보")
    private AlarmData alarmData;

    @Schema(description = "룸의 유저들 정보 리스트")
    private List<RoomDetailRes.UserData> userList;
    @Builder
    @Data
    public static class RoomData {

        @Schema( description = "룸이 가지고 있는 알람의 아이콘",example = "⏰")
        private String icon;

        @Schema(description = "room name", example = "설여대 기상방")
        private String name;

        @Schema(description = "room 소개",example = "소프트웨어융학학과 기상방입니다.")
        private String intro;

        @Schema(description = "room 개설일",example = "2020.02.03")
        private String createdAt;

        @Schema(description = "함께하는 인원수",example = "6")
        private Integer personnel;

        @Schema(description = "룸 초대코드" ,example = "4c905")
        private String invitationCode;


    }

    @Builder
    @Data
    public static class AlarmData {

        @Schema(description = "룸에서 울리는 알람아이디",example = "2")
        private Integer id;

        @Schema(description = "미션의 한국말",example = "자동차")
        private String missionKr;

        @Schema(description = "알람 시간", example = "pm 13:00")
        private String alarmTime;

        @Schema(description = "그룹알람 울리는 날",example = "주중")
        private String alarmDay;


    }



    @Builder
    @Data
    public static class UserData {

        @Schema(description = "유저 Id", example = "1")
        private Integer userId;

        @Schema(description = "유저 이름", example = "조혜온")
        private String userName;

        @Schema(description = "- 신입 병아리 \n - 눈을 반짝이는 곰돌이 \n - 깜찍한 토끼" ,example = "신입 병아리")
        private String theme;

        @Schema(description = "유저의 레벨" , example = "1")
        private Integer level;

        @Schema(description = "방장인지 여부", example = "true")
        private Boolean isHost;


    }
}
