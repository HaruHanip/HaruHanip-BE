package com.haruhanip.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GeneralExceptionResponse {
    private String code;
    private String message;
    private Integer status;

    @Builder
    public GeneralExceptionResponse(String code, String message, Integer status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
