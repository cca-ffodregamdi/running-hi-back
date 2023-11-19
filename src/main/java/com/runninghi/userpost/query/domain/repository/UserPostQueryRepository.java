package com.runninghi.userpost.query.domain.repository;

import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostQueryRepository extends JpaRepository<UserPost, Long> {



}
