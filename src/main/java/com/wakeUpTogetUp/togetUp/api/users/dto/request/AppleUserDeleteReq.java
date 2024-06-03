package com.wakeUpTogetUp.togetUp.api.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppleUserDeleteReq {

    @Schema(description = "apple 코드 ", example = "asdljkfhalsifhwalifj")
    @NotNull(message = "Not null")
    private String authorizationCode;
}
