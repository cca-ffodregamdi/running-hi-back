package com.runninghi.image.command.domain.aggregate.entity;

import com.runninghi.image.command.domain.aggregate.vo.AdminPostVO;
import com.runninghi.image.command.domain.aggregate.vo.UserPostVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_IMAGE")
public class Image {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageNo;

    @Column
    private String imagePath;

    @Embedded
    private AdminPostVO adminPostVO;

    @Embedded
    private UserPostVO userPostVO;

}
