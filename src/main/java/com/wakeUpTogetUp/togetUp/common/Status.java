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
    SUCCESS(HttpStatus.OK, "요청에 성공하였습니다."),
    SUCCESS_CREATED(HttpStatus.CREATED, "생성 되었습니다."),
    SUCCESS_NO_CONTENT(HttpStatus.NO_CONTENT, "결과 데이터가 없습니다."),

    NO_DETECTED_OBJECT(HttpStatus.OK, "탐지된 객체가 없습니다."),
    MISSION_FAILURE(HttpStatus.OK, "미션을 성공하지 못했습니다."),
    MISSION_SUCCESS(HttpStatus.CREATED, "미션을 성공했습니다."),


    /**
     * 300번대(리다이렉트)
     */


    /**
     * 400번대(클라이언트 에러)
     */
    // UNAUTHORIZED
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    UNAUTHORIZED_KAKAO_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 카카오 엑세스토큰입니다."),
    UNAUTHORIZED_APPLE_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 애플 엑세스토큰입니다."),

    // BAD REQUEST
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 값을 확인해주세요."),
    BAD_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "요청 파라미터를 확인해주세요."),
    EMPTY_JWT(HttpStatus.BAD_REQUEST, "빈 jwt 입니다."),
    INVALID_JWT(HttpStatus.BAD_REQUEST, "유효하지 않은 jwt 입니다."),
    JWT_MISMATCH(HttpStatus.BAD_REQUEST, "jwt 정보가 일치하지 않습니다."),
    INVALID_GROUP_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 그룹 id 입니다."),
    INVALID_OBJECT_NAME(HttpStatus.BAD_REQUEST, "유효하지 미션 객체 이름입니다."),
    MISSION_ID_NOT_MATCH(HttpStatus.BAD_REQUEST, "미션과 미션 객체 id 값이 일치하지 않습니다."),
    INVALID_APPLE_CLAIMS(HttpStatus.BAD_REQUEST, "Apple OAuth Claims 값이 올바르지 않습니다."),
    INVALID_APPLE_TOKEN(HttpStatus.BAD_REQUEST, "Apple OAuth Identity Token 형식이 올바르지 않습니다."),
    INVALID_ROOM_HOST_ID(HttpStatus.BAD_REQUEST, "방장의 id가 아닙니다."),
    INVALID_IMAGE(HttpStatus.BAD_REQUEST, "유효한 이미지가 아닙니다."),
    INVALID_FILE_NAME_EXCEPTION(HttpStatus.BAD_REQUEST, "이미지 파일 이름이 유효하지 않습니다."),
    INVALID_FILE_CONTENT_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "이미지 컨텐트 타입이 유효하지 않습니다."),
    APP_VERSION_HIGHER_THAN_LATEST_EXCEPTION(HttpStatus.BAD_REQUEST, "데이터 내의 최신 버전보다 높은 버전입니다."),
    TIME_ZONE_ID_NOT_EXIST(HttpStatus.BAD_REQUEST, "유효하지 않은 시간대입니다."),

    // FORBIDDEN
    USER_AVATAR_LOCKED(HttpStatus.FORBIDDEN, "유저가 보유하지 않은 아바타 ID 입니다."),
    USER_POINT_LACKED(HttpStatus.FORBIDDEN, "포인트가 부족하여 구매가 불가능합니다."),
    USER_ALARM_ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없는 알람입니다."),

    // NOT FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저 입니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 방 입니다."),
    ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 알람 입니다."),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 미션 입니다."),
    AVATAR_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아바타 입니다."),
    MISSION_OBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 미션 객체입니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    ACCOUNT_DOESNT_EXISTS(HttpStatus.NOT_FOUND, "계정이 존재하지 않습니다."),
    ROOM_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "그룹의 해당 멤버가 없습니다."),

    // CONFLICT
    USER_AVATAR_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 보유한 아바타 입니다."),
    ROOM_USER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 방에 들어간 멤버입니다."),
    USER_AVATAR_NOT_ACTIVE(HttpStatus.CONFLICT, "현재 활성화된 유저 아바타가 아닙니다."),
    ROOM_FULL(HttpStatus.CONFLICT,"방의 인원이 전부 찼습니다. "),

    // UNSUPPORTED MEDIA TYPE
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 파일 확장자 입니다."),


    /**
     * 500번대(서버 에러)
     */
    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 에러입니다. 제보 부탁드립니다."),
    IMAGE_ANALYSIS_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 분석을 실패했습니다."),
    FIND_DEFAULT_AVATAR_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "기본 아바타를 불려오는데 실패했습니다."),
    FIND_USER_AVATAR_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "유저의 대표 아바타 정보를 불러오는데 실패했습니다."),
    GET_AVATAR_DEFAULT_SPEECH_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "아바타 기본 대사를 불러오지 못했습니다."),
    GET_AVATAR_SPEECH_BY_CONDITION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "조건의 부합하는 아바타 대사를 불러오지 못했습니다."),
    LOAD_MODEL_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "모델 객체 정보 불러오기를 실패했습니다."),
    FILE_IO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 불러오지 못했습니다."),
    GET_LATEST_APP_VERSION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "앱 최신버전 정보를 불러오는데 실패했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    Status(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
