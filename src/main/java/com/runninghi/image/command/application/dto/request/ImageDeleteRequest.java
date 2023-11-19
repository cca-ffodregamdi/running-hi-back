package com.runninghi.image.command.application.dto.request;

public record ImageDeleteRequest (
        String imageUrl,
        String board
){ }