package com.manulife.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(Integer code) {
        super(code.toString());
    }

    public BadRequestException(String field, String msg) {
        super(String.format("%s %s", field, msg));
    }
}
