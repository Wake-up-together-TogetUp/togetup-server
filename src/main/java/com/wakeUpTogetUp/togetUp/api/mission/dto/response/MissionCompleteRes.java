package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import com.wakeUpTogetUp.togetUp.api.users.vo.UserStat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MissionCompleteRes {
    @Schema(description = "경험치, 레벨, 포인트 정산 결과")
    private UserStat userStat;
}
