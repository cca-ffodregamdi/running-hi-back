package com.runninghi.feedback.command.domain.exception.customException;

public class IllegalArgumentException extends RuntimeException{

    public IllegalArgumentException(String msg) {
        super(msg);
    }

}