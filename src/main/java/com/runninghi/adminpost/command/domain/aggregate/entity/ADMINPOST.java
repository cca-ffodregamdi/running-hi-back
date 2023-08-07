package com.runninghi.adminpost.command.domain.aggregate.entity;

import com.runninghi.adminpost.command.domain.aggregate.vo.WriterNoVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Table(name = "TBL_ADMINPOST")
public class ADMINPOST {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @Embedded
    private WriterNoVO writerNoVO;
    @Column
    private String postTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String postContent;

    @Column
    private String postThumbnail;

}
