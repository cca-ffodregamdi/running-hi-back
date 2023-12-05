package com.runninghi.member.command.domain.repository;

import com.runninghi.member.command.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface MemberCommandRepository extends JpaRepository<Member, UUID> {
}

