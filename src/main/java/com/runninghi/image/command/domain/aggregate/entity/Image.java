package com.runninghi.image.command.domain.aggregate.entity;

import com.runninghi.image.command.domain.aggregate.vo.AdminPostVO;
import com.runninghi.image.command.domain.aggregate.vo.MemberPostVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String imageUrl;

    @Embedded
    private AdminPostVO adminPostVO;

    @Embedded
    private MemberPostVO memberPostVO;

    public Image(Builder builder) {
        this.imageNo = builder.imageNo;
        this.imageUrl = builder.imageUrl;
        this.adminPostVO = builder.adminPostVO;
        this.memberPostVO = builder.memberPostVO;
    }

    public static class Builder {
        private Long imageNo;
        private String imageUrl;
        private AdminPostVO adminPostVO;
        private MemberPostVO memberPostVO;

        public Builder imageNo(Long imageNo) {
            this.imageNo = imageNo;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder adminPostVO(AdminPostVO adminPostVO) {
            this.adminPostVO = adminPostVO;
            return this;
        }

        public Builder memberPostVO(MemberPostVO memberPostVO) {
            this.memberPostVO = memberPostVO;
            return this;
        }

        public Image build() {
            return new Image(this);
        }

    }

}
