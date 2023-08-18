package com.runninghi.feedback.command.application.dto.request;

public record FeedbackReplyCreateRequest(
        Long feedbackNo,
        String feedbackReply
) { }