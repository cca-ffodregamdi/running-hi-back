package com.runninghi;

import com.runninghi.adminpost.query.infrastructure.repository.AdminPostMapper;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.runninghi.adminpost.query.infrastructure.repository")
public class RunninghiApplication implements CommandLineRunner {

    private final AdminPostMapper adminPostMapper;

    public RunninghiApplication(AdminPostMapper adminPostMapper) {
        this.adminPostMapper = adminPostMapper;
    }
    public static void main(String[] args) {
        SpringApplication.run(RunninghiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
