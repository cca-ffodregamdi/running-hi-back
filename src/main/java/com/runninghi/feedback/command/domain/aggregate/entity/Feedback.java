package com.runninghi.feedback.command.domain.aggregate.entity;

import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackCategoryVO;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_FEEDBACK")
public class Feedback {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackNo;

    @Column
    private Date feeabackDate;

    @Column
    private String feedbackTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String feedbackContent;

    @Column
    private Boolean feedbackStatus;

    @Column
    private String feedbackReply;

    @Column
    private Date feedbackReplyDate;

    @Embedded
    private FeedbackWriterVO feedbackWriterVO;

    @Embedded
    private FeedbackCategoryVO feedbackCategoryVO;
}
