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

    @Value("${firebase.project.id")
    private String projectId;

    @PostConstruct
    public void init() {
        try {

            FileInputStream serviceAccount =
                    new FileInputStream(keyPath);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(projectId)
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            throw new IllegalArgumentException("firebase config 오류");
        }
    }
}
