package com.runninghi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class LoggingTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("Logging 테스트 : success")
    public void log4j2LoggerTest() {
        System.out.println("안녕 난 println이야");
        logger.info("안녕 난 logger야");
    }
}
