package com.runninghi.keywordofpost.command.domain.repository;

import com.runninghi.keywordofpost.command.domain.aggregate.entity.KeywordOfPost;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.AdminPostVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeywordOfPostCommandRepository extends JpaRepository<KeywordOfPost, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "delete " +
                     "from tbl_keyword_of_post " +
                    "where admin_post_no = :adminPostNo", nativeQuery = true)
    List<KeywordOfPost> deleteKeywordOfPost(@Param("adminPostNo") Long adminPostNo);


}
