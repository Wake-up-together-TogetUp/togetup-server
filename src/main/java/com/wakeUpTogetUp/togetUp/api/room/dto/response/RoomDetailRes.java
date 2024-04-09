package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Schema(description = "룸정보 조회 요청 api 응답")
@Getter
@AllArgsConstructor(staticName = "of")
public class RoomDetailRes {

    @Schema(description = "룸 정보")
    private RoomData roomData;

    @Schema(description = "미션 정보")
    private MissionData missionData;

    @Schema(description = "룸의 유저들 정보 리스트")
    private List<UserProfileData> userProfileData;

    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class RoomData {



        @Schema(description = "room name", example = "설여대 기상방")
        private String name;

        @Schema(description = "room 소개", example = "소프트웨어융학학과 기상방입니다.")
        private String intro;

        @Schema(description = "room 개설일", example = "2020.02.03")
        private LocalDate createdAt;

        @Schema(description = "함께하는 인원수", example = "6")
        private Integer headCount;

        @Schema(description = "룸 초대코드", example = "4c905")
        private String invitationCode;


    }

    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class MissionData {


        @Schema(description = "미션 아이콘", example = "⏰")
        private String icon;

        @Schema(description = "미션의 한국말", example = "자동차")
        private String missionKr;

    }


}
