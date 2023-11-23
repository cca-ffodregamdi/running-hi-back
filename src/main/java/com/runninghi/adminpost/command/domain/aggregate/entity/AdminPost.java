package com.runninghi.adminpost.command.domain.aggregate.entity;

import com.runninghi.adminpost.command.domain.aggregate.vo.WriterNoVO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private String adminPostThumbnailUrl;

    @Column
    private double totalDistance;

    @Column
    private double totalTime;

    @Column
    private double maxSlope;

    @Column
    private double kcal;

    @Column
    private String createDate;

    @Column
    private String updateDate;

    @Builder
    public AdminPost(Long adminPostNo, WriterNoVO writerNoVO, String adminPostTitle, String adminPostContent, String adminPostThumbnailUrl,
                     double totalDistance, double totalTime, double maxSlope, double kcal, String createDate, String updateDate) {
        this.adminPostNo = adminPostNo;
        this.writerNoVO = writerNoVO;
        this.adminPostTitle = adminPostTitle;
        this.adminPostContent = adminPostContent;
        this.adminPostThumbnailUrl = adminPostThumbnailUrl;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.maxSlope = maxSlope;
        this.kcal = kcal;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public void update (String adminPostTitle, String adminPostContent, String adminPostThumbnailUrl, double totalDistance, double totalTime,
                        double maxSlope, double kcal) {
        this.adminPostTitle = adminPostTitle;
        this.adminPostContent = adminPostContent;
        this.adminPostThumbnailUrl = adminPostThumbnailUrl;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.maxSlope = maxSlope;
        this.kcal = kcal;
        this.updateDate = new Date().toString();

    }
}
