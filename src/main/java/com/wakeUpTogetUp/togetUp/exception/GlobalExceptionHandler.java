package com.wakeUpTogetUp.togetUp.exception;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import org.hibernate.TypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GlobalExceptionHandler {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Catch Custom Exception(BaseException)
    @ExceptionHandler({BaseException.class})
    protected BaseResponse<Status> handleCustomException(BaseException exception) {
        logger.debug("BaseResponse exception occurred: {}", exception.getMessage(), exception);
        exception.printStackTrace();

        return new BaseResponse<>(exception.getStatus());
    }

    // Catch Bad Request Exception
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MissingRequestHeaderException.class,
            IllegalStateException.class,
            IllegalArgumentException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MultipartException.class,
            NoHandlerFoundException.class,
    })
    protected BaseResponse<Status> handleBadRequestException(Exception exception) {
        logger.debug("Bad request exception occurred: {}",
                exception.getMessage(),
                exception);
        exception.printStackTrace();

        return new BaseResponse<>(Status.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected BaseResponse<Status> handleNotSupportedMediaTypeException(HttpMediaTypeNotSupportedException exception) {
        logger.debug("Bad request exception occurred: {}",
                exception.getMessage(),
                exception);
        exception.printStackTrace();

        return new BaseResponse<>(Status.UNSUPPORTED_MEDIA_TYPE);
    }

    // vallidation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected BaseResponse validException(MethodArgumentNotValidException exception) {
        String msg = "유효성 검사 실패 : " + exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        exception.printStackTrace();

        return new BaseResponse(Status.BAD_REQUEST, msg);
    }

    // Catch all Exception
    @ExceptionHandler({Exception.class})
    protected BaseResponse<Status> handleServerException(Exception exception) {
        logger.error("Unexpected exception occurred: {}",
                exception.getMessage(),
                exception);
        exception.printStackTrace();

        return new BaseResponse<>(Status.INTERNAL_SERVER_ERROR);
    }
}