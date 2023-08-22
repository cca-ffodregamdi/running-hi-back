package com.runninghi.feedback.query.application.dto.response;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;

import java.util.Date;

public record FeedbackStatusResponse(

        Long feedbackNo,
        Date feedbackDate,
        String feedbackTitle,
        String feedbackContent,
        Boolean feedbackStatus,
        String feedbackReply,
        Date feedbackReplyDate,
        int feedbackReportCnt,
        FeedbackWriterVO feedbackWriterVO,
        FeedbackCategory feedbackCategory,
        String nickname

) {
    public static FeedbackStatusResponse from (Feedback feedback, String nickname) {
        return new FeedbackStatusResponse(
                feedback.getFeedbackNo(),
                feedback.getFeedbackDate(),
                feedback.getFeedbackTitle(),
                feedback.getFeedbackContent(),
                feedback.getFeedbackStatus(),
                feedback.getFeedbackReply(),
                feedback.getFeedbackReplyDate(),
                feedback.getFeedbackReportCnt(),
                feedback.getFeedbackWriterVO(),
                feedback.getFeedbackCategory(),
                nickname
        );
    }
}
