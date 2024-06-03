package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserProfileData {

    @Schema(description = "유저 Id", example = "1")
    private Integer userId;

    @Schema(description = "유저 이름", example = "조혜온")
    private String userName;
    @Schema(description = "- SENIOR_CHICK \n - ASTRONAUT_BEAR \n - LOVELY_BUNNY \n - RAINY_DAY_PUPPY \n - PHILOSOPHER_RACCOON", example = "GLUTTON_PANDA")
    private String theme;

    @Schema(description = "유저의 레벨", example = "1")
    private Integer level;

    public UserProfileData(Integer userId, String userName, String avatarTheme, Integer level) {
        this.userId = userId;
        this.userName = userName;
        this.theme = avatarTheme;
        this.level = level;
    }
}

