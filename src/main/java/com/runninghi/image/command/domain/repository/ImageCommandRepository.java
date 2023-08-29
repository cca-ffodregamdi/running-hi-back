package com.runninghi.image.command.domain.repository;

import com.runninghi.image.command.domain.aggregate.entity.Image;
import com.runninghi.image.command.domain.aggregate.vo.AdminPostVO;
import com.runninghi.image.command.domain.aggregate.vo.UserPostVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageCommandRepository extends JpaRepository<Image, Long> {

    // 특정 유저 게시물과 관련된 이미지들 전부 삭제
    void deleteImagesByUserPostVO(UserPostVO userPostVO);

    // 특정 관리자 게시물과 관련된 이미지들 전부 삭제
    void deleteImagesByAdminPostVO(AdminPostVO adminPostVO);

}
