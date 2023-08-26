package com.runninghi.keywordofpost.command.domain.aggregate.entity;

import com.runninghi.keywordofpost.command.domain.aggregate.vo.AdminPostVO;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.KeywordVO;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.UserPostVO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_KEYWORD_OF_POST")
@ToString
public class KeywordOfPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordOfPostNo;

    @Embedded
    private AdminPostVO adminPostVO;

    @Embedded
    private UserPostVO userPostVO;

    @Embedded
    private KeywordVO keywordVO;

    @Builder
    public KeywordOfPost(Long keywordOfPostNo, AdminPostVO adminPostVO, UserPostVO userPostVO, KeywordVO keywordVO) {
        this.keywordOfPostNo = keywordOfPostNo;
        this.adminPostVO = adminPostVO;
        this.userPostVO = userPostVO;
        this.keywordVO = keywordVO;
    }
}
