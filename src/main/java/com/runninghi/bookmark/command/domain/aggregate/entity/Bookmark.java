package com.runninghi.bookmark.command.domain.aggregate.entity;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "TBL_BOOKMARK")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Bookmark implements Serializable {

    @EmbeddedId
    private BookmarkVO bookmark;

    @Column
    private Long userNo;

    @Column
    private LocalDate addDate;

    public Bookmark(BookmarkVO bookmark) {
        this.bookmark = bookmark;
    }

    public Bookmark(Long folderNo, Long postNo) {
        this.bookmark = new BookmarkVO(folderNo, postNo);
    }
}
