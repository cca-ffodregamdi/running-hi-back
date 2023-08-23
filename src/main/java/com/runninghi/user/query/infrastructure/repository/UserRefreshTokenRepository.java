package com.runninghi.user.query.infrastructure.repository;

import com.runninghi.user.command.domain.aggregate.entity.UserRefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, UUID> {
    Optional<UserRefreshToken> findByUserIdAndReissueCountLessThan(UUID id, long count);
}
