package com.runninghi.common.handler.feedback.customException;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }

}