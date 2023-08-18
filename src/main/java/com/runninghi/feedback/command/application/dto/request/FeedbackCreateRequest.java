package com.runninghi.feedback.command.application.dto.request;

public record FeedbackCreateRequest(
        String feedbackTitle,
        String feedbackContent,
        int feedbackCategory
) {
}
