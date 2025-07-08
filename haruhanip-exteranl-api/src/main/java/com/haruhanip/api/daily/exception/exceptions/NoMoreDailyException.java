package com.haruhanip.api.daily.exception.exceptions;

import com.haruhanip.api.daily.exception.DailyErrorCode;
import com.haruhanip.common.exception.BusinessException;
import lombok.Getter;

@Getter
public class NoMoreDailyException extends BusinessException {
    public NoMoreDailyException() {
        super(DailyErrorCode.NO_MORE_DAILY);
    }
}
