package com.runninghi.common.handler.feedback;

import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.common.handler.feedback.customException.NotMatchWriterException;
import com.runninghi.common.handler.feedback.customException.UnauthorizedAccessException;
import com.runninghi.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FeedbackExceptionResponseHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(NotMatchWriterException.class)
    public ResponseEntity<ApiResponse> handleNotMatchWriterException(NotMatchWriterException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ApiResponse.error(e.getMessage()));
    }

}
