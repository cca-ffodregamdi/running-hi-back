package com.runninghi.feedback.command.application.dto.request;

public record FeedbackUpdateRequest(
        Long feedbackNo,
        String feedbackTitle,
        String feedbackContent,
        int feedbackCategory
) { }
