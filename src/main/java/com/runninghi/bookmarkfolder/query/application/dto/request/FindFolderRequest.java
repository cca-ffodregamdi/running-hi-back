package com.runninghi.bookmarkfolder.query.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
public record FindFolderRequest (
        @Schema(description = "폴더 번호", example = "러닝코스")
        Long folderNo ){


}