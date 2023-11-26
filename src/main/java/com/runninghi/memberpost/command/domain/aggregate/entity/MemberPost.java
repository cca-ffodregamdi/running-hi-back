package com.runninghi.memberpost.command.domain.aggregate.entity;

import com.runninghi.memberpost.command.domain.aggregate.vo.MemberVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_MEMBER_POST")
public class MemberPost {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberPostNo;

    @Column
    private Date memberPostDate;

    @Column
    private String memberPostTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String memberPostContent;

    @Embedded
    private MemberVO memberVO;
}
