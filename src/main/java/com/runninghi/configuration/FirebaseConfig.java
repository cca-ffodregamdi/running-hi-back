package com.runninghi.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials.path}")
    private String keyPath;

    @Value("${firebase.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream(keyPath);

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setProjectId(projectId)
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase 초기화 성공");
            } else {
                System.out.println("Firebase 앱이 이미 초기화되어 있습니다.");
            }

        } catch (Exception e) {
            System.err.println("Firebase 초기화 실패: " + e.getMessage());
            throw new IllegalArgumentException("Firebase 설정 오류", e);
        }
    }
}
