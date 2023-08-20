package com.runninghi.keyword.command.domain.repository;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandKeywordRepository extends JpaRepository<Keyword, Long> {
}
