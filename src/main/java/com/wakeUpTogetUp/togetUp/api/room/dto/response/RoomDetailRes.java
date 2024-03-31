package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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
public class RoomDetailRes {

    @Schema(description = "룸 정보")
    private RoomData roomData;

    @Schema(description = "미션 정보")
    private MissionData missionData;

    @Schema(description = "룸의 유저들 정보 리스트")
    private List<RoomDetailRes.UserData> userList;

    @Builder
    @Data
    public static class RoomData {



        @Schema(description = "room name", example = "설여대 기상방")
        private String name;

        @Schema(description = "room 소개", example = "소프트웨어융학학과 기상방입니다.")
        private String intro;

        @Schema(description = "room 개설일", example = "2020.02.03")
        private String createdAt;

        @Schema(description = "함께하는 인원수", example = "6")
        private Integer headCount;

        @Schema(description = "룸 초대코드", example = "4c905")
        private String invitationCode;


    }

    @Builder
    @Data
    public static class MissionData {


        @Schema(description = "미션 아이콘", example = "⏰")
        private String icon;

        @Schema(description = "미션의 한국말", example = "자동차")
        private String missionKr;

    }


    @Builder
    @Data
    public static class UserData {

        @Schema(description = "유저 Id", example = "1")
        private Integer userId;

        @Schema(description = "유저 이름", example = "조혜온")
        private String userName;

        @Schema(description = "- 신입 병아리 \n - 눈을 반짝이는 곰돌이 \n - 깜찍한 토끼", example = "신입 병아리")
        private String theme;

        @Schema(description = "유저의 레벨", example = "1")
        private Integer level;


    }
}
