package com.wakeUpTogetUp.togetUp.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200번대(성공)
     */
    // 공통
    SUCCESS(HttpStatus.OK, "요청에 성공하였습니다."),

    /**
     * 300번대(리다이렉트)
     */


    /**
     * 400번대(클라이언트 에러)
     */
    // 공통
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 값을 확인해주세요."),
    // files
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "삭제할 파일을 찾을 수 없습니다."),

    // alarms
    ALARM_INVALID_USER_ID(HttpStatus.BAD_REQUEST, "해당하는 유저를 찾을 수 없습니다."),

    /**
     * 500번대(서버 에러)
     */
    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 에러입니다. 제보 부탁드립니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;

    private BaseResponseStatus(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
