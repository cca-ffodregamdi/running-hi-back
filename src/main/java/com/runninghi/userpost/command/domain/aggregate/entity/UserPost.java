package com.runninghi.userpost.command.domain.aggregate.entity;

import com.runninghi.userpost.command.domain.aggregate.vo.UserVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_USER_POST")
public class UserPost {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPostNo;

    @Column
    private Date userPostDate;

    @Column
    private String userPostTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String userPostContent;

    @Embedded
    private UserVO userVO;
}
