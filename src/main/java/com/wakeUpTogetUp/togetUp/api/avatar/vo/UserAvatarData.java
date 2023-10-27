package com.wakeUpTogetUp.togetUp.api.avatar.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "유저 아바타 목록 가져오기")
@Getter
@Setter
@NoArgsConstructor
public class UserAvatarData {

    @Schema(description = "아바타 Id")
    private int avatarId;

    @Schema(description = "아바타 테마 이름")
    private String theme;

    @Schema(description = "가격")
    private int price;

    @Schema(description = "해금 레벨")
    private int unlockLevel;

    @Schema(description = "유저 보유 여부")
    private Boolean isUnlocked;
}
