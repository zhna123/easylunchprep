package com.zhna123.easylunchprep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class LunchboxNotFoundException extends RuntimeException {
    public LunchboxNotFoundException(String message) {
        super(message);
    }

}
