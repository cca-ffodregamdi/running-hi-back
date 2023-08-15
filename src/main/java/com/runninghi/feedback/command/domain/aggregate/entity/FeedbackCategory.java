package com.runninghi.feedback.command.domain.aggregate.entity;

import com.runninghi.feedback.command.domain.exception.customException.IllegalArgumentException;

public enum FeedbackCategory {

    INQUIRY(0),
    PROPOSAL(1),
    WEBERROR(2),
    ROUTEERROR(3),
    POSTERROR(4);

    private int value;

    FeedbackCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FeedbackCategory fromValue(int value) {
        for (FeedbackCategory feedbackCategory : FeedbackCategory.values()) {
            if (feedbackCategory.getValue() == value) {
                return feedbackCategory;
            }
        }
        throw new IllegalArgumentException("카테고리 번호가 올바르지않습니다.");
    }

    @Override
    public String toString() {
        switch (this.value) {
            case 0:
                return "문의 사항";
            case 1:
                return "개선 사항";
            case 2:
                return "웹페이지 오류";
            case 3:
                return "경로 오류";
            case 4:
                return "게시글 내용 오류";
        }

        throw new IllegalArgumentException("피드백 카테고리가 올바르지않습니다.");
    }

}
