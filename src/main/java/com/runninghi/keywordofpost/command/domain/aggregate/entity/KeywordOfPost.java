package com.runninghi.keywordofpost.command.domain.aggregate.entity;

import com.runninghi.keywordofpost.command.domain.aggregate.vo.AdminPostVO;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.KeywordVO;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.UserPostVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Table(name = "TBL_KEYWORD_OF_POST")
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
}
