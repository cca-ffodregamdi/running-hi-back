package com.runninghi.keyword.query.infrastructure.repository;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryKeywordRepository extends JpaRepository<Keyword, Long> {
}
