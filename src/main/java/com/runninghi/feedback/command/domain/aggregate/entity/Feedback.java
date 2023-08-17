package com.runninghi.feedback.command.domain.aggregate.entity;

import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_FEEDBACK")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackNo;

    @Column
    private Date feedbackDate;

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

    @Column
    private int feedbackReportCnt;

    @Embedded
    private FeedbackWriterVO feedbackWriterVO;

    @Enumerated(EnumType.ORDINAL)
    private FeedbackCategory feedbackCategory;

    public Feedback(Builder builder) {
        this.feedbackNo = builder.feedbackNo;
        this.feedbackDate = builder.feedbackDate;
        this.feedbackTitle = builder.feedbackTitle;
        this.feedbackContent = builder.feedbackContent;
        this.feedbackStatus = builder.feedbackStatus;
        this.feedbackReply = builder.feedbackReply;
        this.feedbackReplyDate = builder.feedbackReplyDate;
        this.feedbackWriterVO = builder.feedbackWriterVO;
        this.feedbackCategory = builder.feedbackCategory;
        this.feedbackReportCnt = builder.feedbackReportCnt;
    }

    public static class Builder {
        private Long feedbackNo;
        private Date feedbackDate;
        private String feedbackTitle;
        private String feedbackContent;
        private Boolean feedbackStatus;
        private String feedbackReply;
        private Date feedbackReplyDate;
        private int feedbackReportCnt;
        private FeedbackWriterVO feedbackWriterVO;
        private FeedbackCategory feedbackCategory;

        public Builder feedbackNo (Long feedbackNo) {
            this.feedbackNo = feedbackNo;
            return this;
        }

        public Builder feedbackDate (Date feedbackDate) {
            this.feedbackDate = feedbackDate;
            return this;
        }

        public Builder feedbackTitle (String feedbackTitle) {
            this.feedbackTitle = feedbackTitle;
            return this;
        }

        public Builder feedbackContent (String feedbackContent) {
            this.feedbackContent = feedbackContent;
            return this;
        }

        public Builder feedbackStatus (Boolean feedbackStatus) {
            this.feedbackStatus = feedbackStatus;
            return this;
        }

        public Builder feedbackReply (String feedbackReply) {
            this.feedbackReply = feedbackReply;
            return this;
        }

        public Builder feedbackReplyDate (Date feedbackReplyDate) {
            this.feedbackReplyDate = feedbackReplyDate;
            return this;
        }

        public Builder feedbackReportCnt (int feedbackReportCnt) {
            this.feedbackReportCnt = feedbackReportCnt;
            return this;
        }

        public Builder feedbackWriterVO (FeedbackWriterVO feedbackWriterVO) {
            this.feedbackWriterVO = feedbackWriterVO;
            return this;
        }

        public Builder feedbackCategory (FeedbackCategory feedbackCategory) {
            this.feedbackCategory = feedbackCategory;
            return this;
        }

        public Feedback build() {
            return new Feedback(this);
        }

    }

}
