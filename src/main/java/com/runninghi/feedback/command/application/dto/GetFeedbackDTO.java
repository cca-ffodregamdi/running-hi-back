package com.runninghi.feedback.command.application.dto;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GetFeedbackDTO {

    Long feedbackNo;
    Feedback findFeedback;

}
