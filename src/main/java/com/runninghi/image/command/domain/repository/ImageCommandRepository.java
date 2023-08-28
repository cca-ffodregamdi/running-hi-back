package com.runninghi.image.command.domain.repository;

import com.runninghi.image.command.domain.aggregate.entity.Image;
import com.runninghi.image.command.domain.aggregate.vo.UserPostVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageCommandRepository extends JpaRepository<Image, Long> {

    void deleteImagesByUserPostVO(UserPostVO userPostVO);

}
