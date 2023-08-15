package com.runninghi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RefreshScope
public class CloudConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public void profileConfig() {
        this.getConfig();
    }

    public Map<String, Object> getConfig() {
        Map<String, Object> configMap = new HashMap<>();
        log.info("==> config-info: profile={}, testKey={}", url, username);

        configMap.put("profile", url);
        configMap.put("testKey", username);

        return configMap;
    }

}
