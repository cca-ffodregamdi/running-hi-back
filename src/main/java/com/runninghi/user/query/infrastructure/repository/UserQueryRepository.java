package com.runninghi.user.query.infrastructure.repository;

import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserQueryRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByAccount(String account);

    List<User> findAllUserByRole(Role role);
}
