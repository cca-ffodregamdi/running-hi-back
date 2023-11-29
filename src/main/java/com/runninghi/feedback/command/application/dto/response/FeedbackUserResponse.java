package com.runninghi.feedback.command.application.dto.response;

import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record FeedbackUserResponse(

        UUID id,
        Role role,
        String name,
        String account,
        LocalDateTime createdAt

) {
}
