package com.studybuddy.subject_trainer.feign_clients;

import lombok.Data;

@Data
public class TopicDTO {
    public TopicDTO(long chapterId, String name){
        this.chapterId = chapterId;
        this.name = name;
    }
    private String name;
    private Long chapterId;
}
