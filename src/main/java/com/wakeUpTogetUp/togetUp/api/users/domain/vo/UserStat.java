package com.wakeUpTogetUp.togetUp.api.users.domain.vo;

import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserStat {
    @Schema(description = "현재 레벨")
    private final int level;

    @Schema(description = "현재 경험치(%)")
    private final double expPercentage;

    private UserStat(User user) {
        this.level = user.getLevel();
        this.expPercentage = user.getExpPercentage();
    }

    public static UserStat from(User user) {
        return new UserStat(user);
    }
}
