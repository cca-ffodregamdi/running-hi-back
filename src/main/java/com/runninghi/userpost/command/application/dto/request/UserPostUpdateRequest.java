package com.runninghi.userpost.command.application.dto.request;

public record UserPostUpdateRequest(
        Long userPostNo,
        String userPostTitle,
        String userPostContent
) { }
