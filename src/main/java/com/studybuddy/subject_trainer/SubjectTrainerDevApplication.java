package com.studybuddy.subject_trainer;

import com.studybuddy.subject_trainer.configurations.DevContainersConfig;
import org.springframework.boot.SpringApplication;

public class SubjectTrainerDevApplication {
    public static void main(String[] args) {
        SpringApplication.from(SubjectTrainerApplication::main)
                .with(DevContainersConfig.class)
                .run(args);
    }
}
