package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
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


    @Schema(description = "룸아이디", example = "1")
    private Integer id;

    @Schema(description = "미션 아이콘", example = "⏰")
    private String icon;

    @Schema(description = "room name", example = "설여대 기상방")
    private String name;

    @Schema(description = "room 소개", example = "소프트웨어융학학과 기상방입니다.")
    private String intro;

    @Schema(description = "room 개설일", example = "2020.02.03")
    private String createdAt;

    @Schema(description = "함께하는 인원수", example = "6")
    private Integer headCount;

    @Schema(description = "미션 객체 id", example = "1")
    private Integer missionObjectId;

    @Schema(description = "미션의 한국말", example = "자동차")
    private String missionKr;


}
