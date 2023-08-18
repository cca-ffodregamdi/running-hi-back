package com.runninghi.keyword.command.domain.repository;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

}
