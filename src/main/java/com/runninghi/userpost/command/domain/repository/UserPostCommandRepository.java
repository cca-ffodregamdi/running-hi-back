package com.runninghi.userpost.command.domain.repository;

import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPostCommandRepository extends JpaRepository<UserPost, Long> {


    Optional<UserPost> findByUserPostNo(Long userPostNo);

}
