package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMissionWithObjectListRes {
    private int id;
    @Schema(description = "미션명")
    private String name;
    private String createdAt;
    private Boolean isActive;
    @Schema(description = "해당 미션이 인식 가능한 객체 목록")
    private List<GetMissionObjectRes> missionObjectResList;
}
