package com.runninghi.adminpost.command.domain.aggregate.entity;

import com.runninghi.adminpost.command.domain.aggregate.vo.WriterNoVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_ADMINPOST")
public class AdminPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminPostNo;

    @Embedded
    private WriterNoVO writerNoVO;
    @Column
    private String adminPostTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String adminPostContent;

    @Column
    private String adminPostThumbnail;

    @Builder
    public AdminPost(Long adminPostNo, WriterNoVO writerNoVO, String adminPostTitle, String adminPostContent, String adminPostThumbnail) {
        this.adminPostNo = adminPostNo;
        this.writerNoVO = writerNoVO;
        this.adminPostTitle = adminPostTitle;
        this.adminPostContent = adminPostContent;
        this.adminPostThumbnail = adminPostThumbnail;
    }
}
