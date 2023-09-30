package com.runninghi.keywordofpost.command.domain.repository;

import com.runninghi.keywordofpost.command.domain.aggregate.entity.KeywordOfPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordOfPostCommandRepository extends JpaRepository<KeywordOfPost, Long> {
}
