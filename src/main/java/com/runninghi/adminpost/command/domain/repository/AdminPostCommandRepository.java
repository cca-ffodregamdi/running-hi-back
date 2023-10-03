package com.runninghi.adminpost.command.domain.repository;

import com.runninghi.adminpost.command.domain.aggregate.entity.AdminPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPostCommandRepository extends JpaRepository<AdminPost, Long> {
}
