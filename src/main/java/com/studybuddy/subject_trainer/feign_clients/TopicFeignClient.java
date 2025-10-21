package com.studybuddy.subject_trainer.feign_clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "topic-trainer", path = "topics")
public interface TopicFeignClient {
    @PostMapping
    Iterable<TopicDTO> post(@RequestBody Iterable<TopicDTO> topicDTO, @RequestParam Long chapterId);

    @GetMapping("{id}")
    TopicDTO get(@PathVariable Long id);

    @PutMapping("{id}")
    TopicDTO put(@RequestBody TopicDTO topicDTO, @PathVariable Long id);

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id);

    @DeleteMapping
    void deleteAllByChapterId(@RequestParam Long chapterId);
}
