package com.runninghi.user.query.infrastructure.repository;

import com.runninghi.user.command.domain.aggregate.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, UUID> {
    Optional<UserRefreshToken> findByMemberIdAndReissueCountLessThan(UUID id, long count);
}
