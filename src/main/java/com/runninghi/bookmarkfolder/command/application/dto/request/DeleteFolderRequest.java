package com.runninghi.bookmarkfolder.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record DeleteFolderRequest (
        @Schema(description = "폴더 번호", example = "러닝코스")
        Long folderNo ){

    }
