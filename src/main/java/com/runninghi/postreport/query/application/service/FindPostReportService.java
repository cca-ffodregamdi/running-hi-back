package com.runninghi.postreport.query.application.service;

import com.runninghi.postreport.command.application.dto.ResponsePostReportDTO;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPostReportService {

    private PostReportRepository postReportRepository;

    public ResponsePostReportDTO findPostReport(Long postReportNo) {

        // 설명. findById는 Optional<PostReport> 형태로 객체 반환. get()을 통해 Optional 안의 객체를 꺼냄.
        PostReport findedPostReport = postReportRepository.findById(postReportNo).get();

        //isPresent, get 둘 다 쓰고싶은데 같은 메소드 두번 호출 안하는 방법?

        // 설명. Entity -> DTO 변환
        ResponsePostReportDTO responsePostReportDTO = new ResponsePostReportDTO(findedPostReport);

        return responsePostReportDTO;
    }
}
