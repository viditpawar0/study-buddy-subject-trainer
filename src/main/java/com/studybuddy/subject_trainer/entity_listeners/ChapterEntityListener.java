package com.studybuddy.subject_trainer.entity_listeners;

import com.studybuddy.subject_trainer.entities.Chapter;
import com.studybuddy.subject_trainer.feign_clients.TopicFeignClient;
import jakarta.persistence.PreRemove;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ChapterEntityListener {
    private final TopicFeignClient topicFeignClient;

    public ChapterEntityListener(@Lazy TopicFeignClient topicFeignClient) {
        this.topicFeignClient = topicFeignClient;
    }

    @PreRemove
    public void postRemove(Chapter chapter) {
        topicFeignClient.deleteAllByChapterId(chapter.getId());
    }
}
