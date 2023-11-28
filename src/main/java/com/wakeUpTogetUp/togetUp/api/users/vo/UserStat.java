package com.wakeUpTogetUp.togetUp.api.users.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserStat {
    @Schema(description = "현재 레벨")
    private int level;

    @Schema(description = "현재 경험치")
    private int experience;

    @Schema(description = "포인트")
    private int point;
}
