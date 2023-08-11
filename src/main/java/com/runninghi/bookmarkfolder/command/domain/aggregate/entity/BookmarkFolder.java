package com.runninghi.bookmarkfolder.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;



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
    private Long userNo;

    @Builder
    public BookmarkFolder(String folderName, Long userNo) {
        this.folderName = folderName;
        this.userNo = userNo;
    }

}
