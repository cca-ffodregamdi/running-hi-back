package com.runninghi.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

@SpringBootTest
@Transactional
class GpxParserTest {

    GpxParser gpxParser = new GpxParser();

    @DisplayName("gpx parser 테스트")
    @Test
    void testGPXparser() throws ParserConfigurationException {

        File gpxFile = new File("src/main/resources/test.gpx");

        // when & then
        gpxParser.parseGPXFile(gpxFile);
    }

}