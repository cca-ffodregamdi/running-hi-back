package com.runninghi.comment.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @Column
    private UUID userNo;

    @Column
    private Long userPostNo;

    @Column
    private LocalDate commentDate;

    @Column
    private String commentContent;

    @Column
    private int commentReportCnt;

    @Builder
    public Comment(Long commentNo, UUID userNo, Long userPostNo, LocalDate commentDate, String commentContent, int commentReportCnt) {
        this.commentNo = commentNo;
        this.userNo = userNo;
        this.userPostNo = userPostNo;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
        this.commentReportCnt = commentReportCnt;
    }
}
