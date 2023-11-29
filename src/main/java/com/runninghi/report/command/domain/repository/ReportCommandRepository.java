package com.runninghi.report.command.domain.repository;

import com.runninghi.report.command.domain.aggregate.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCommandRepository extends JpaRepository<Report, Long> {

//    List<Report> findAllByReportedContentVO(ReportedContentVO reportedContentVO);
}
