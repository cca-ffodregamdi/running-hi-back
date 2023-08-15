package com.runninghi.postreport.command.application.service;

import com.runninghi.postreport.command.domain.repository.PostReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
public class PostReportServiceTests {

    @Autowired
    private PostReportRepository postReportRepository;
    @Test
    @DisplayName("게시글 신고 저장 테스트")
    void savePostReport() {

    }

}
