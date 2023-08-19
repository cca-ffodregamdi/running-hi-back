package com.runninghi.bookmarkfolder.command.domain.aggregate.entity;


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

    @Column
    private UUID userNo;

    @Builder
    public BookmarkFolder(Long folderNo, String folderName, UUID userNo) {
        this.folderNo = folderNo;
        this.folderName = folderName;
        this.userNo = userNo;
    }

}
