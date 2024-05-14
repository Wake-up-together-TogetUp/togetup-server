package com.wakeUpTogetUp.togetUp.api.users.model;

import java.util.function.Function;

public class UserProgressCalculator {

    private static final Function<User, Integer> LEVEL_UP_THRESHOLD_CALCULATOR =
            user -> 10 + 16 * (user.getLevel() - 1);

    public static int calculateLevelUpThreshold(User user) {
        return LEVEL_UP_THRESHOLD_CALCULATOR.apply(user);
    }
}