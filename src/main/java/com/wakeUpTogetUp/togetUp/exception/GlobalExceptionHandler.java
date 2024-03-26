package com.wakeUpTogetUp.togetUp.exception;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // Catch Custom Exception(BaseException)
    @ExceptionHandler({BaseException.class})
    protected BaseResponse<Status> handleCustomException(BaseException exception) {
        log.debug("BaseResponse exception occurred: {}", exception.getMessage(), exception);
        exception.printStackTrace();

        return new BaseResponse<>(exception.getStatus());
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MissingRequestHeaderException.class,
            IllegalStateException.class,
            IllegalArgumentException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MultipartException.class,
            NoHandlerFoundException.class})
    protected BaseResponse<Status> handleBadRequestException(Exception exception) {
        log.debug("Bad request exception occurred: {}", exception.getMessage(), exception);
        exception.printStackTrace();

        return new BaseResponse<>(Status.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected BaseResponse<Map> handleConstraintViolationException(ConstraintViolationException exception) {
        log.debug("Bad request exception occurred: {}", exception.getMessage(), exception);
        exception.printStackTrace();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Validation error");
        body.put("details", exception.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList()));

        return new BaseResponse<>(Status.BAD_REQUEST, body);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected BaseResponse<Status> handleNotSupportedMediaTypeException(HttpMediaTypeNotSupportedException exception) {
        log.debug("Bad request exception occurred: {}",
                exception.getMessage(),
                exception);
        exception.printStackTrace();

        return new BaseResponse<>(Status.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected BaseResponse<String> validException(MethodArgumentNotValidException exception) {
        String msg = "유효성 검사 실패 : " + exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        exception.printStackTrace();

        return new BaseResponse<>(Status.BAD_REQUEST, msg);
    }

    @ExceptionHandler({IOException.class})
    protected BaseResponse<Status> handleIOException(IOException exception) {
        log.error("IO exception occurred: {}", exception.getMessage(), exception);
        exception.printStackTrace();

        return new BaseResponse<>(Status.FILE_IO_EXCEPTION);
    }

    @ExceptionHandler({Exception.class})
    protected BaseResponse<Status> handleServerException(Exception exception) {
        log.error("Unexpected exception occurred: {}",
                exception.getMessage(),
                exception);
        exception.printStackTrace();

        return new BaseResponse<>(Status.INTERNAL_SERVER_ERROR);
    }
}