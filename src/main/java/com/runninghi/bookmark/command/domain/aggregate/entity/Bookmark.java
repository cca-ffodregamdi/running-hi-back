package com.runninghi.bookmark.command.domain.aggregate.entity;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkMemberVO;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private BookmarkVO bookmarkVO;

    @Embedded
    private BookmarkMemberVO memberNoVO;

    @Column
    private LocalDate addDate;

    @Builder
    public Bookmark(BookmarkVO bookmarkVO, BookmarkMemberVO memberNoVO, LocalDate addDate) {
        this.bookmarkVO = bookmarkVO;
        this.memberNoVO = memberNoVO;
        this.addDate = addDate;
    }
}
