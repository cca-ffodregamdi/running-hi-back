package com.runninghi.feedback.command.domain.aggregate.entity;

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
        throw new IllegalArgumentException();
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

        throw new IllegalArgumentException();
    }
}
