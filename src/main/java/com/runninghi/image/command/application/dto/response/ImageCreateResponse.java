package com.runninghi.image.command.application.dto.response;

public record ImageCreateResponse (
        String imageUrl
) {
    public static ImageCreateResponse from(String imageUrl) {
        return new ImageCreateResponse(
                imageUrl
        );
    }
}
