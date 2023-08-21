package com.runninghi.feedback.query.application.dto.request;

public record FeedbackFindRequest(
        Long feedbackNo,
        Integer feedbackCategory
) { }
