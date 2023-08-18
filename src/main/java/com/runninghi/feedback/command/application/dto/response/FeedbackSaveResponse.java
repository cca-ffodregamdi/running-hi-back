package com.runninghi.feedback.command.application.dto.response;

public record FeedbackSaveResponse (
        Long feedbackNo,
        String feedbackTitle,
        String feedbackContent,
        int feedbackCategory
)
{ }
