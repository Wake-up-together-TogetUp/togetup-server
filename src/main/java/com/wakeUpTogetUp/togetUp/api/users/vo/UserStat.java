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

    @Schema(description = "보유 코인")
    private final int coin;

    private UserStat(User user) {
        this.level = user.getLevel();
        this.expPercentage = user.calculateExpPercentage();
        this.coin = user.getCoin();
    }

    public static UserStat from(User user) {
        return new UserStat(user);
    }
}
