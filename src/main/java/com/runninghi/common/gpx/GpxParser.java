package com.runninghi.common.gpx;

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

    // 설명. GPX 파일 parser
    public List<GpxDataDTO> parseGPXFile(File file) {

        List<GpxDataDTO> points = new ArrayList<>();
        GpxDataDTO prevPoint = null;
        int settingTimeDiff = 5;

        Document doc = xmlParser(file);
        NodeList trkpts = doc.getElementsByTagName("trkpt");
        int trkptsLength = trkpts.getLength();
        for (int i=0; i<trkptsLength; i++) {
            Node trkpt = trkpts.item(i);    // trkpt

            GpxDataDTO currentPoint = parseCurrentPointFromTrkpt(trkpt, prevPoint, settingTimeDiff);

            if (currentPoint != null) {
                points.add(currentPoint);
                prevPoint = currentPoint;
                System.out.println("currentPoint = " + currentPoint);
            }
        }

        return points;
    }

    private GpxDataDTO parseCurrentPointFromTrkpt(Node trkpt, GpxDataDTO prevPoint, int settingTimeDiff) {

        // 필기. 위도, 경도 추출
        double lat = parseLatOrLonFromTrkpt(trkpt, "lat");
        double lon = parseLatOrLonFromTrkpt(trkpt, "lon");

        // 필기. 고도, 시간 추출
        NodeList childNodes = trkpt.getChildNodes();    // ele, time
        int childNodesLength = childNodes.getLength();
        double ele = parseElevationFromNodeList(childNodes, childNodesLength);
        String time = parseTimeFromNodeList(childNodes, childNodesLength);

        // 필기. 현재 포인트 저장
        int timeDifference = calTimeDifference(prevPoint, time);
        if (prevPoint == null || (timeDifference >= settingTimeDiff)) {  // 필기. 이전 포인트와 현재 포인트의 시간 차이가 설정한 초 이상이라면,
            GpxDataDTO currentPoint = createCurrentPoint(lat, lon, ele, time, timeDifference,prevPoint);
            return currentPoint;
        }

        return null;
    }

    // 설명. xml parser build
    private Document xmlParser(File file) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  // 팩토리 초기화
//            Document doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(gpx)));
            doc = factory.newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();

        } catch (SAXException e ) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        return doc;
    }

    // 설명. Double 형태인지 확인
    private boolean checkValidParseDouble(String str) {

        boolean result = Pattern.matches("^[0-9]+(.)?[0-9]*$", str);

        return result;
    }

    // 설명. 위도 경도 추출
    private double parseLatOrLonFromTrkpt(Node trkpt, String kind) {

        if (kind.equals("lat")) {
            String latStr = trkpt.getAttributes().getNamedItem("lat").getNodeValue();
            if (checkValidParseDouble(latStr)) {
                double lat = Double.parseDouble(latStr);
                return lat;
            }
        }

        if (kind.equals("lon")) {
            String lonStr = trkpt.getAttributes().getNamedItem("lon").getNodeValue();
            if (checkValidParseDouble(lonStr)) {
                double lon = Double.parseDouble(lonStr);
                return lon;
            }
        }
        return 0;
    }

    // 설명. 고도 추출
    private double parseElevationFromNodeList(NodeList childNodes, int childNodesLength) {

        for (int j = 0; j < childNodesLength; j++) {
            Node childNode = childNodes.item(j);

            if (childNode.getNodeName().equals("ele") && !childNode.getTextContent().equals("")) {
                String eleStr = childNode.getTextContent();
                double ele = parseDoubleElevation(eleStr);
                return ele;
            }
        }

        return 0;
    }

    // 설명. 고도 double로 추출
    private double parseDoubleElevation(String eleStr) {

        if (checkValidParseDouble(eleStr)) {
            double ele = Double.parseDouble(eleStr);
            return ele;
        }
        return 0;
    }

    // 설명. 측정 시간 추출
    private String parseTimeFromNodeList(NodeList childNodes, int childNodesLength) {

        for (int j = 0; j < childNodesLength; j++) {
            Node childNode = childNodes.item(j);

            if (childNode.getNodeName().equals("time") && !childNode.getTextContent().equals("")) {
                String timeStr = childNode.getTextContent();
                String time = timeStr.substring(timeStr.indexOf("T") + 1, timeStr.indexOf("Z"));
                return time;
            }
        }
        return "";
    }

    private GpxDataDTO createCurrentPoint(double lat, double lon, double ele, String time, int timeDifference, GpxDataDTO prevPoint) {
        GpxDataDTO currentPoint = new GpxDataDTO();
        currentPoint.setLatitude(lat);
        currentPoint.setLongitude(lon);
        currentPoint.setElevation(ele);
        currentPoint.setMeasureTime(time);
        currentPoint.setTimeDifference(timeDifference);

        if (prevPoint != null) {
            double distance = calDistance(prevPoint.getLatitude(), prevPoint.getLongitude(), lat, lon);
            double eleDifference = calElevationDifference(prevPoint.getElevation(), ele);
            double slope = calSlope(eleDifference, distance);

            currentPoint.setDistance(distance);
            currentPoint.setSlope(slope);
        }

        return currentPoint;
    }

    // 설명. 하버사인 공식(Haversine Formula, 두 지표 사이 거리 구하는 공식)
    private double calDistance(double prevLat, double prevLon, double currentLat, double currentLon) {

        final int R = 6371; // 지구 반지름(km)

        // 설명. 위도, 경도 차이 구하기
        double latDistance = Math.toRadians(currentLat - prevLat);
        double lonDistance = Math.toRadians(currentLon - prevLon);

        // 설명. 하버사인 공식(이동 거리 = 호 길이를 구하기 위한 라디안 값)
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(prevLat)) * Math.cos(Math.toRadians(currentLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 설명. 실제 이동 거리(호 길이)
        return R * c * 1000; // m단위로 변환
    }

    // 설명. 이동 시간 구하기
    private int calTimeDifference(GpxDataDTO prevPoint, String currentTime) {

        if (prevPoint == null) {
            return 0;
        }
        if (currentTime.isEmpty()) {  // 시간이 빈 칸일 때
            return 0;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date prevDate = sdf.parse(prevPoint.getMeasureTime());
            Date currentDate = sdf.parse(currentTime);
            long diff = currentDate.getTime() - prevDate.getTime();    // ms 단위
            int timeDifference = (int) (diff / 1000); // 초 단위로 변환
            return timeDifference;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 설명. 고도 차이 구하기
    private double calElevationDifference(double prevElevation, double currentElevation) {
        return currentElevation - prevElevation;
    }

    // 설명. 경사도 계산하기
    private double calSlope(double elevationDifference, double distance) {
        if (distance == 0) {
            return 0;
        }
        return (elevationDifference / distance) * 100;  // %
    }

}
