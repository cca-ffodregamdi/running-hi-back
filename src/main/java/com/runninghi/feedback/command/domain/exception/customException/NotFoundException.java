package com.runninghi.feedback.command.domain.exception.customException;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }

}