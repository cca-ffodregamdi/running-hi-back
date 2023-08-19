package com.runninghi.bookmark.command.domain.aggregate.entity;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    @Column
    private UUID userNo;

    @Column
    private LocalDate addDate;

    @Builder
    public Bookmark(BookmarkVO bookmarkVO, UUID userNo, LocalDate addDate) {
        this.bookmarkVO = bookmarkVO;
        this.userNo = userNo;
        this.addDate = addDate;
    }
}
