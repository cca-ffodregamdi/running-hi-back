package com.runninghi.common;

public class GpxParser {

    // 설명. 하버사인 공싱(Haversine Formula, 두 지표 사이 거리 구하는 공식)
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

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


}
