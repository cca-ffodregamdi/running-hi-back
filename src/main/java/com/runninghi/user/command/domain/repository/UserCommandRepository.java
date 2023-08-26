package com.runninghi.user.command.domain.repository;

import com.runninghi.user.command.domain.aggregate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface UserCommandRepository extends JpaRepository<User, UUID> {
}
