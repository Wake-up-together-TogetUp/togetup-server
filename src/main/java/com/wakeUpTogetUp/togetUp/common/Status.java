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
    MISSION_OBJECT_NOT_FOUND(HttpStatus.OK, "탐지된 객체가 없습니다."),
    MISSION_FAILURE(HttpStatus.OK, "미션을 성공하지 못했습니다."),
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
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다."),
    UNAUTHORIZED_KAKAO_TOKEN(HttpStatus.UNAUTHORIZED,"유효하지않은 카카오 엑세스토큰입니다."),

    // 유효하지 않은 요청
    INVALID_GROUP_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 그룹 id 입니다."),
    INVALID_MISSION_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 미션 id 입니다."),
    INVALID_MISSION_OBJECT_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 미션 객체 id 입니다."),
    MISSION_ID_NOT_MATCH(HttpStatus.BAD_REQUEST, "미션과 미션 객체 id 값이 일치하지 않습니다."),
    Invalid_APPLE_Token(HttpStatus.BAD_REQUEST,"Apple OAuth Claims 값이 올바르지 않습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 유저 입니다."),
    ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 알람 입니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    ACCOUNT_DOESNT_EXISTS(HttpStatus.NOT_FOUND,"계정이 존재하지 않습니다."),
    OBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "객체를 찾을 수 없습니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 파일 확장자 입니다."),


    /**
     * 500번대(서버 에러)
     */
    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 에러입니다. 제보 부탁드립니다."),
    LOAD_MODEL_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "모델 객체 정보 불러오기를 실패했습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;

    private Status(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
