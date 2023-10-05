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

    @Schema( description = "룸이 가이고 있는 알람의 아이콘",example = "⏰")
    private String icon;

    @Schema(description = "room name", example = "설여대 기상방")
    private String name;

    @Schema(description = "room 소개",example = "소프트웨어융학학과 기상방입니다.")
    private String intro;

    @Schema(description = "room 개설일",example = "개설일 2020.02.03")
    private String createdAtString;

    @Schema(description = "함께하는 인원수",example = "6명이 함께해요")
    private String personnelString;

    private List<RoomDetailRes.UserData> userList;

    @Builder
    @Data
    public static class UserData {

        @Schema(description = "유저 Id", example = "1")
        private Integer userId;

        @Schema(description = "유저 이름", example = "조혜온")
        private String userName;

        @Schema(description = "방장인지 여부", example = "true")
        private Boolean isHost;


    }
}
