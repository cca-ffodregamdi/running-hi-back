package com.runninghi.bookmark.command.domain.aggregate.entity;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkUserVO;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Table(name = "TBL_BOOKMARK")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Bookmark implements Serializable {

    @EmbeddedId
    private BookmarkVO bookmarkVO;

    @Embedded
    private BookmarkUserVO userNo;

    @Column
    private LocalDate addDate;

    @Builder
    public Bookmark(BookmarkVO bookmarkVO, BookmarkUserVO userNo, LocalDate addDate) {
        this.bookmarkVO = bookmarkVO;
        this.userNo = userNo;
        this.addDate = addDate;
    }
}
