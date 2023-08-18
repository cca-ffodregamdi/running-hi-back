package com.runninghi.feedback.command.application.dto.request;

public record FeedbackReplyUpdateRequest(
        Long feedbackNo,
        String feedbackReply
) { }
