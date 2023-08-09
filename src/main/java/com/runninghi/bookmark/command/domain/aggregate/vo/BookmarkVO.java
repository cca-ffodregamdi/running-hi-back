package com.runninghi.bookmark.command.domain.aggregate.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
