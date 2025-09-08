package com.studybuddy.subject_trainer;

import com.studybuddy.subject_trainer.feign_clients.TopicFeignClient;
import io.awspring.cloud.s3.S3Template;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("scratch1")
public class ScratchController {
    @Autowired
    private S3Template s3Template;
    @Autowired
    ChatModel chatModel;
    @Autowired
    TopicFeignClient topicFeignClient;
    @GetMapping
    public String  test() {
        return "Hello from Subject Trainer!";
    }
    @GetMapping("chat-completion")
    public String chatCompletion(@RequestBody String prompt) {
        return chatModel.call(prompt);
    }
}
