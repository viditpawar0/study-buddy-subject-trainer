package com.studybuddy.subject_trainer.scratch;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("scratch")
public class ScratchController {
    @Autowired
    ChatModel chatModel;

    @Autowired
    private ChatClient chatClient;

    private record Person(String name, String country) {}

    @GetMapping
    public String scratch() {
        return chatClient.prompt(new Prompt(
                new SystemMessage("Please teach Addition to the user"),
                new UserMessage("")
        )).call().content();
    }

    @GetMapping("chat-completion")
    public String chatCompletion(@RequestBody String prompt) {
        return chatModel.call(prompt);
    }
}
