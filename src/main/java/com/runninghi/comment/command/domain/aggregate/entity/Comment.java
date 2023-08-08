package com.runninghi.comment.command.domain.aggregate.entity;

import com.runninghi.comment.command.domain.aggregate.vo.CommentReportCntVO;
import com.runninghi.comment.command.domain.aggregate.vo.UserNoVO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Table(name = "TBL_COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @Embedded
    private UserNoVO userNo;

    @Column
    private Long postNo;

    @Column
    private LocalDate commentDate;

    @Column
    private String commentContent;

    @Embedded
    private CommentReportCntVO commentReportCnt;
}
