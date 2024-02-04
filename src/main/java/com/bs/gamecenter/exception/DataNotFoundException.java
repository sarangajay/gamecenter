package com.bs.gamecenter.exception;

import lombok.ToString;

@ToString(callSuper = true)
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
