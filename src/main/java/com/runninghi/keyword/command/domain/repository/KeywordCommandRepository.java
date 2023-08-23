package com.runninghi.keyword.command.domain.repository;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KeywordCommandRepository extends JpaRepository<Keyword, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE " +
                     "FROM tbl_keyword " +
                    "WHERE KEYWORD_NO = :keywordNo", nativeQuery = true)
    int costumDeleteByKeywordNo(@Param("keywordNo") Long keywordNo);

}
