package com.haruhanip.api.daily.exception;

import com.haruhanip.api.daily.exception.exceptions.NoMoreDailyException;
import com.haruhanip.common.ErrorCode;
import com.haruhanip.common.exception.BusinessException;
import com.haruhanip.common.exception.GeneralExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DailyExceptionHandler {

    @ExceptionHandler( value = {
            NoMoreDailyException.class
    })
    protected ResponseEntity<GeneralExceptionResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        GeneralExceptionResponse response = GeneralExceptionResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus()) // Bad Request
                .build();

        return ResponseEntity.ok().body(response);
    }

}
