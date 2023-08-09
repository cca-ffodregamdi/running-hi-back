package com.runninghi.bookmarkfolder.command.domain.aggregate.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Table(name = "TBL_BOOKMARK_FOLDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BookmarkFolder{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folderNo;

    @Column
    private String folderName;

    @Column
    private Long userNo;


}
