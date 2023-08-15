package com.runninghi.feedback.command.application.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SaveFeedbackDTO {

    private String feedbackTitle;
    private String feedbackContent;
    private int feedbackCategory;

}