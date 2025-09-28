package com.studybuddy.subject_trainer.feign_clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "topic-trainer", path = "topics")
public interface TopicFeignClient {
    @PostMapping
    TopicDTO postTopic(@RequestBody TopicDTO topicDTO);
    @GetMapping("{id}")
    TopicDTO getTopic(@PathVariable Long id);
    @PutMapping
    TopicDTO putTopic(@RequestBody TopicDTO topicDTO);
    @DeleteMapping("{id}")
    void deleteTopicById(@PathVariable Long id);
}
