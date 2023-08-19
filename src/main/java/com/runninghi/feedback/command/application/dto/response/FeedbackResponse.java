package com.runninghi.feedback.command.application.dto.response;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;

import java.util.Date;

public record FeedbackResponse(
        Long feedbackNo,
        Date feedbackDate,
        String feedbackTitle,
        String feedbackContent,
        Boolean feedbackStatus,
        String feedbackReply,
        Date feedbackReplyDate,
        int feedbackReportCnt,
        FeedbackWriterVO feedbackWriterVO,
        FeedbackCategory feedbackCategory
) {

    public static FeedbackResponse from (Feedback feedback) {
        return new FeedbackResponse(
                feedback.getFeedbackNo(),
                feedback.getFeedbackDate(),
                feedback.getFeedbackTitle(),
                feedback.getFeedbackContent(),
                feedback.getFeedbackStatus(),
                feedback.getFeedbackReply(),
                feedback.getFeedbackReplyDate(),
                feedback.getFeedbackReportCnt(),
                feedback.getFeedbackWriterVO(),
                feedback.getFeedbackCategory()
        );
    }
}
