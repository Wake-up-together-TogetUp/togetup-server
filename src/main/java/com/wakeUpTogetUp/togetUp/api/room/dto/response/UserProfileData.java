package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import com.wakeUpTogetUp.togetUp.api.avatar.AvatarTheme;
import com.wakeUpTogetUp.togetUp.api.room.UserCompleteType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserProfileData {

    @Schema(description = "유저 Id", example = "1")
    private Integer userId;

    @Schema(description = "유저 이름", example = "조혜온")
    private String userName;

    @Schema(description = "- 신입 병아리 \n - 눈을 반짝이는 곰돌이 \n - 깜찍한 토끼", example = "신입 병아리")
    private String theme;

    @Schema(description = "유저의 레벨", example = "1")
    private Integer level;

    public UserProfileData(Integer userId, String userName, AvatarTheme avatarTheme, Integer level) {
        this.userId = userId;
        this.userName = userName;
        this.theme = avatarTheme.getValue();
        this.level = level;
    }
}

