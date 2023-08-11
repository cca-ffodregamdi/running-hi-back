package com.runninghi.common;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class GpxParser {

    // 설명. 하버사인 공식(Haversine Formula, 두 지표 사이 거리 구하는 공식)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // 지구 반지름(km)

        // 설명. 위도, 경도 차이 구하기
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // 설명. 하버사인 공식(이동 거리 = 호 길이를 구하기 위한 라디안 값)
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 설명. 실제 이동 거리(호 길이)
        return R * c * 1000; // m단위로 변환
    }

    // 설명. 이동 시간 구하기
    private int calTimeDifference(String time1, String time2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long diff = date2.getTime() - date1.getTime();	// ms 단위
            int timeDifference = (int) (diff / 1000); // 초 단위로 변환
            return timeDifference;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 설명. 고도 차이 구하기
    private double calElevationDifference(double ele1, double ele2) {
        return Math.abs(ele1 - ele2);
    }

    // 설명. 경사도 계산하기
    private double calSlope(double altitudeDifference, double distance) {
        if (distance == 0) {
            return 0;
        }
        return (altitudeDifference / distance) * 100;
    }

    private boolean canIParseDouble(String str) {

        boolean result = Pattern.matches("^[a-zA-Zㄱ-ㅎ가-힣]*$", str);

        return result;
    }

    // 설명. GPX 파일 parser
    public List<GpxDataDTO> parseGPXFile(File file) {

        List<GpxDataDTO> points = new ArrayList<>();
        GpxDataDTO prevPoint = new GpxDataDTO();
        int settingTimeDiff = 5;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  // 팩토리 초기화
//            Document doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(gpx)));
            Document doc = factory.newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();

            NodeList trkpts = doc.getElementsByTagName("trkpt");

            int trkptsLength = trkpts.getLength();
            for (int i=0; i<trkptsLength; i++) {
                Node trkpt = trkpts.item(i);

                double lat = Double.parseDouble(trkpt.getAttributes().getNamedItem("lat").getNodeValue());
                double lon = Double.parseDouble(trkpt.getAttributes().getNamedItem("lon").getNodeValue());
                double ele = 0;
                String time = "";

                NodeList childNodes = trkpt.getChildNodes();

                int childNodesLength = childNodes.getLength();
                for (int j=0; j<childNodesLength; j++) {
                    Node childNode = childNodes.item(j);
                    String resultText = childNode.getTextContent();

                    if (childNode.getNodeName().equals("ele")) {
                        if (canIParseDouble(resultText)) {
                            ele=0;
                        } else {
                            ele = Double.parseDouble(resultText);
                        }
                        System.out.println("ele = " + ele);
                    }

                    if (childNode.getNodeName().equals("time")) {
                        if (resultText == null) {time ="";}
                        time = childNode.getTextContent();
                        System.out.println("time = " + time);
                    }

                }

            }



        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        return null;

    }


}
