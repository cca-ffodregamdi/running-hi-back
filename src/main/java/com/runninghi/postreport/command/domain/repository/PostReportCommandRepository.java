package com.runninghi.postreport.command.domain.repository;

import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostReportCommandRepository extends JpaRepository<PostReport, Long> {

    List<PostReport> findAllByReportedPostVO(ReportedPostVO reportedPostVO);
}
