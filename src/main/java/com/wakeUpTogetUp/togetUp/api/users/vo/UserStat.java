package com.wakeUpTogetUp.togetUp.api.users.vo;

import com.wakeUpTogetUp.togetUp.api.users.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserStat {
    @Schema(description = "현재 레벨")
    private final int level;

    @Schema(description = "현재 경험치(%)")
    private final double expPercentage;

    @Schema(description = "포인트")
    private final int point;

    private UserStat(User user) {
        this.level = user.getLevel();
        this.expPercentage = user.calculateExpPercentage();
        this.point = user.getPoint();
    }

    public static UserStat from(User user) {
        return new UserStat(user);
    }
}
