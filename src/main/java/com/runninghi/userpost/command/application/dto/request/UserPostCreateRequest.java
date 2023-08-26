package com.runninghi.userpost.command.application.dto.request;

public record UserPostCreateRequest(
        String userPostTitle,
        String userPostContent
) {
}
