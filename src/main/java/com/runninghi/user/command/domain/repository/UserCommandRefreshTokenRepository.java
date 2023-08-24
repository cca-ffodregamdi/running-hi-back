package com.runninghi.user.command.domain.repository;

import com.runninghi.user.command.domain.aggregate.entity.UserRefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Transactional
public interface UserCommandRefreshTokenRepository extends JpaRepository<UserRefreshToken, UUID> {

}