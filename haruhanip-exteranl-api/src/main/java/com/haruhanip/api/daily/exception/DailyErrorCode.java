package com.haruhanip.api.daily.exception;

import com.haruhanip.common.ErrorCode;

public enum DailyErrorCode implements ErrorCode {
    NO_MORE_DAILY("NO_MORE_DAILY", "더 이상 일일 문제를 가져올 수 없습니다.", 204);

    private final String code;
    private final String message;
    private final int status;

    DailyErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
