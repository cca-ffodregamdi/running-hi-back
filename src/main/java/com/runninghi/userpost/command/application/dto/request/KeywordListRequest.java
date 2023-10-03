package com.runninghi.userpost.command.application.dto.request;

public record KeywordListRequest(
        Long keywordNo,
        String keywordName
) {
    public static KeywordListRequest of (Long keywordNo, String keywordName) {
        return new KeywordListRequest(keywordNo, keywordName);
    }
}