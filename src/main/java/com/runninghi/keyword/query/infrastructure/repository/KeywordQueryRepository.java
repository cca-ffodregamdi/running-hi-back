package com.runninghi.keyword.query.infrastructure.repository;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordQueryRepository extends JpaRepository<Keyword, Long> {
}
