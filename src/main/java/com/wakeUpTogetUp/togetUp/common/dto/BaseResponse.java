package com.wakeUpTogetUp.togetUp.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.Status;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"httpStatusCode", "httpReasonPhrase", "message", "images/result"})
public class BaseResponse<T> {
    private final int httpStatusCode;
    private final String httpReasonPhrase;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // result 없을 시
    public BaseResponse(Status status) {
        this.httpStatusCode = status.getHttpStatus().value();
        this.httpReasonPhrase = status.getHttpStatus().getReasonPhrase();
        this.message = status.getMessage();
    }

    // result 있을 시
    public BaseResponse(Status status, T result) {
        this.httpStatusCode = status.getHttpStatus().value();
        this.httpReasonPhrase = status.getHttpStatus().getReasonPhrase();
        this.message = status.getMessage();
        this.result = result;
    }

    // BaseException 발생 시
    public BaseResponse(BaseException exception) {
        this.httpStatusCode = exception.getStatus().getHttpStatus().value();
        this.httpReasonPhrase = exception.getStatus().getHttpStatus().getReasonPhrase();
        this.message = exception.getStatus().getMessage();
    }

    // TODO : 정적 메소드 만들기
}
