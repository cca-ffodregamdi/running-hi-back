package com.runninghi.bookmarkfolder.command.domain.aggregate.entity;


import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.vo.FolderUserVO;
import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Getter
@Table(name = "TBL_BOOKMARK_FOLDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BookmarkFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folderNo;

    @Column
    private String folderName;

    @Embedded
    private FolderUserVO userNo;

    @Builder
    public BookmarkFolder(Long folderNo, String folderName, FolderUserVO userNo) {
        this.folderNo = folderNo;
        this.folderName = folderName;
        this.userNo = userNo;
    }

    public void update(UpdateFolderRequest folderRequest) {
        this.folderName = folderRequest.folderName();
    }

}
