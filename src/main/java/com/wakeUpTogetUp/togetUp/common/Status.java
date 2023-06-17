package com.wakeUpTogetUp.togetUp.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum Status {
    /**
     * 200번대(성공)
     */
    // 공통
    SUCCESS(HttpStatus.OK, "요청에 성공하였습니다."),
    SUCCESS_CREATED(HttpStatus.CREATED, "생성 되었습니다."),
    // Mission
    MISSION_UNSUCCESS(HttpStatus.OK, "미션을 성공하지 못했습니다."),
    MISSION_SUCCESS(HttpStatus.OK, "미션을 성공했습니다."),

    /**
     * 300번대(리다이렉트)
     */


    /**
     * 400번대(클라이언트 에러)
     */
    // 공통
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 값을 확인해주세요."),
    BAD_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "요청 파라미터를 확인해주세요."),
    EMPTY_JWT(HttpStatus.BAD_REQUEST, "빈 jwt 입니다."),
    INVALID_JWT(HttpStatus.BAD_REQUEST, "유효하지 않은 jwt 입니다."),
    JWT_MISMATCH(HttpStatus.BAD_REQUEST, "jwt 정보가 일치하지 않습니다."),

    // 유효하지 않은 요청
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 유저 id 입니다."),
    INVALID_GROUP_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 그룹 id 입니다."),
    INVALID_MISSION_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 미션 id 입니다."),
    INVALID_ALARM_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 알람 id 입니다."),
    INVALID_ROUTINE_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 루틴 id 입니다."),

    // Alarm
    ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 알람을 찾을 수 없습니다."),
    // Routine
    ROUTINE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 루틴을 찾을 수 없습니다."),
    // File
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "삭제할 파일을 찾을 수 없습니다."),
    ACCOUNT_DOESNT_EXISTS(HttpStatus.NOT_FOUND,"계정이 존재하지 않습니다."),


    /**
     * 500번대(서버 에러)
     */
    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 에러입니다. 제보 부탁드립니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;

    private Status(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
