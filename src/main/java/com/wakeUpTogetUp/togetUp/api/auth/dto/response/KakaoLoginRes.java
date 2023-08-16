package com.wakeUpTogetUp.togetUp.api.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginRes {
    private String id;
    @Builder.Default
    private KakaoLoginData kakao_account = KakaoLoginData.builder().build();

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoLoginData {

        @Builder.Default
        private KakaoProfile profile = KakaoProfile.builder().build();
        @Builder.Default
        private KakaoPropery properties = KakaoPropery.builder().build();
        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class KakaoProfile {
            private String nickname;
        }

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class KakaoPropery {
            private String nickname;
        }
    }

}
