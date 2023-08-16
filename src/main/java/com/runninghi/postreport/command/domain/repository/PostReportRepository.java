package com.runninghi.postreport.command.domain.repository;

import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, Long> {

}
