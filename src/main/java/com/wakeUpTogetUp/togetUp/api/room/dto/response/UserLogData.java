package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import com.wakeUpTogetUp.togetUp.api.room.UserCompleteType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserLogData {

    @Schema(description = "유저 Id", example = "1")
    private Integer userId;

    @Schema(description = "유저 이름", example = "조혜온")
    private String userName;

    @Schema(description = "유저의 미션 수행 상태 (성공 SUCCESS, 나머지 DEFAULT)", example = "SUCCESS")
    private UserCompleteType userCompleteType;

    @Schema(description = "미션 수행 사진", example = "http://~~~")
    private String missionPicLink;


    public static UserLogData of(Integer userId, String userName, String missionPicLink) {

        return new UserLogData(
                userId,
                userName,
                (missionPicLink !="" ? UserCompleteType.SUCCESS : UserCompleteType.DEFAULT),
                missionPicLink);
    }

}

