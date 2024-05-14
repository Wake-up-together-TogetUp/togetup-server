package com.wakeUpTogetUp.togetUp.api.users.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserProgressResult {

    private final boolean isUserLevelUp;
}
