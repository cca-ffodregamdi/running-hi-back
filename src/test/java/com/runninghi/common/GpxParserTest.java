package com.runninghi.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

class GpxParserTest {

    GpxParser gpxParser = new GpxParser();

    @DisplayName("gpx parser 테스트 : success")
    @Test
    void testGPXparser() throws ParserConfigurationException {

        // given
        File gpxFile = new File("src/main/resources/gpxtest/test.gpx");

        // when & then
        Assertions.assertDoesNotThrow(
                () -> gpxParser.parseGPXFile(gpxFile)
        );
    }

    @DisplayName("gpx parser 테스트 : 파일 내 기록에 이상한 값이 들어가있을 때도 저장이 되는지")
    @Test
    void testWrongGPXparser() {

        // given
        File worngGpxFile = new File("src/main/resources/gpxtest/wrongTest.gpx"); // 기록 중간에 글자가 있고 빈 칸이 있음.

        // when & then
        Assertions.assertDoesNotThrow(
                () -> gpxParser.parseGPXFile(worngGpxFile)
        );

    }

}