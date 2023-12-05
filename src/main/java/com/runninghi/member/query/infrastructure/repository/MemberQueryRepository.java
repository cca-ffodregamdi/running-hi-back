package com.runninghi.member.query.infrastructure.repository;

import com.runninghi.member.command.domain.aggregate.entity.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface MemberQueryRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findAccountByNameAndEmail(String name, String email);

    Optional<Member> findUserByAccountAndEmail(String account, String email);

    Optional<Member> findUserByAccount(String account);

    List<Member> findAllUserByRole(Role role);

}
