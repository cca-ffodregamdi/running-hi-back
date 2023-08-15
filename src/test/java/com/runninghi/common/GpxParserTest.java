package com.runninghi.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

@SpringBootTest
class GpxParserTest {

    GpxParser gpxParser = new GpxParser();

    @DisplayName("gpx parser 테스트")
    @Test
    void testGPXparser() throws ParserConfigurationException {

        // given
        File gpxFile = new File("src/main/resources/gpxtest/test.gpx");

        // when & then
        Assertions.assertDoesNotThrow(
                () -> gpxParser.parseGPXFile(gpxFile)
        );
    }

}