package com.runninghi.userpost.query.application.dto.response;

import java.util.Date;
import java.util.UUID;

public record UserPostComment(
        Long commentNo,
        UUID commentWriterNo,
        String commentWriterNickname,
        String commentContent,
        Date commentDate
) {
}
