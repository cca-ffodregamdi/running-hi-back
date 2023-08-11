package com.runninghi.bookmarkfolder.command.application.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveFolderDTO {

    private String folderName;
    private Long userNo;

}
