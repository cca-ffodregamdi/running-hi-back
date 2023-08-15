package com.runninghi.feedback.command.application.dto;

import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FeedbackDTO {

    Long feedbackNo;
    String feedbackTitle;
    String feedbackCategory;
    Date feedbackDate;
    Long userNo;
    String nickName;

    public FeedbackDTO(Long feedbackNo, Date feedbackDate, String feedbackTitle, FeedbackCategory feedbackCategory,
                       Long userNo, String nickName) {
        this.feedbackNo = feedbackNo;
        this.feedbackDate = feedbackDate;
        this.feedbackTitle = feedbackTitle;
        this.feedbackCategory = feedbackCategory.toString();
        this.userNo = userNo;
        this.nickName = nickName;
    }

}