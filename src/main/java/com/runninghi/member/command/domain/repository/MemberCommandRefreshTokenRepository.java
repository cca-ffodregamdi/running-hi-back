package com.runninghi.member.command.domain.repository;

import com.runninghi.member.command.domain.aggregate.entity.MemberRefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Transactional
public interface MemberCommandRefreshTokenRepository extends JpaRepository<MemberRefreshToken, UUID> {

}