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
    private Date userPostModifiedDate;

    @Column
    private String userPostTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String userPostContent;

    @Embedded
    private UserVO userVO;

    public UserPost(Builder builder) {
        this.userPostNo = builder.userPostNo;
        this.userPostDate = builder.userPostDate;
        this.userPostModifiedDate = builder.userPostModifiedDate;
        this.userPostTitle = builder.userPostTitle;
        this.userPostContent = builder.userPostContent;
        this.userVO = builder.userVO;
    }

    public static class Builder {
        private Long userPostNo;
        private Date userPostDate;
        private Date userPostModifiedDate;
        private String userPostTitle;
        private String userPostContent;
        private UserVO userVO;

        public Builder userPostNo (Long userPostNo) {
            this.userPostNo = userPostNo;
            return this;
        }

        public Builder userPostDate (Date userPostDate) {
            this.userPostDate = userPostDate;
            return this;
        }

        public Builder userPostModifiedDate (Date userPostModifiedDate) {
            this.userPostModifiedDate = userPostModifiedDate;
            return this;
        }

        public Builder userPostTitle (String userPostTitle) {
            this.userPostTitle = userPostTitle;
            return this;
        }

        public Builder userPostContent (String userPostContent) {
            this.userPostContent = userPostContent;
            return this;
        }

        public Builder userVO(UserVO userVO) {
            this.userVO = userVO;
            return this;
        }

        public UserPost build() {
            return new UserPost(this);
        }
    }

}
