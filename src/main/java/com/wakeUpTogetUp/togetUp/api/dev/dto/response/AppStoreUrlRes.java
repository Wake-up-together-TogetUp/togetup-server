package com.wakeUpTogetUp.togetUp.api.dev.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppStoreUrlRes {

    @Schema(description = "최신 버전 여부")
    private Boolean isLatest;

    @Schema(description = "앱스토어 링크", nullable = true)
    private String url;

    private AppStoreUrlRes(Boolean isLatest) {
        this.isLatest = isLatest;
    }

    private AppStoreUrlRes(Boolean isLatest, String url) {
        this.isLatest = isLatest;
        this.url = url;
    }
}
