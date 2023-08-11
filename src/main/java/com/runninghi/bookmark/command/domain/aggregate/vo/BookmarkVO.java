package com.runninghi.bookmark.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
public class BookmarkVO implements Serializable {

    @Column
    private Long folderNo;

    @Column
    private Long postNo;

    public BookmarkVO(Long folderNo, Long postNo) {
        this.folderNo = folderNo;
        this.postNo = postNo;
    }
}
