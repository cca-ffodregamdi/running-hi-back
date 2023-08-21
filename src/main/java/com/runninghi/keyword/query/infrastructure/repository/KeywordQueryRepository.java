package com.runninghi.keyword.query.infrastructure.repository;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KeywordQueryRepository extends JpaRepository<Keyword, Long> {

    @Query(value = "SELECT KEYWORD_NO," +
                    "      KEYWORD_NAME " +
                    " FROM TBL_KEYWORD " +
                    "WHERE KEYWORD_NAME = :keywordName", nativeQuery = true)
    Optional<Keyword> findKeywordByKeywordName(@Param("keywordName") String keywordName);
}
