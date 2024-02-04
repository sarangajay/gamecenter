package com.bs.gamecenter.exception;

import lombok.ToString;

@ToString(callSuper = true)
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
