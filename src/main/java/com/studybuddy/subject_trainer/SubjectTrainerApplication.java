package com.studybuddy.subject_trainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SubjectTrainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubjectTrainerApplication.class, args);
    }

}
