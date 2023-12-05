package com.runninghi.member.query.infrastructure.repository;

import com.runninghi.member.command.domain.aggregate.entity.MemberRefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface MemberQueryRefreshTokenRepository extends JpaRepository<MemberRefreshToken, UUID> {
    Optional<MemberRefreshToken> findByMemberIdAndReissueCountLessThan(UUID id, long count);
}
