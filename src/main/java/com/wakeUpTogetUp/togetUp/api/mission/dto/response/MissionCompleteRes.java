package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import com.wakeUpTogetUp.togetUp.api.users.domain.vo.UserStat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MissionCompleteRes {
    @Schema(description = "경험치, 레벨, 포인트 정산 결과")
    private UserStat userStat;

    @Schema(description = "레벨 업 여부")
    private boolean isUserLevelUp;

    @Schema(description = "아바타 해금 여부")
    private boolean isAvatarUnlockAvailable;
}
