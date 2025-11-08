package com.studybuddy.subject_trainer.scratch;

import com.google.firebase.auth.FirebaseAuth;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("scratch")
public class ScratchController {
    @Autowired
    ChatModel chatModel;

    @GetMapping("chat-completion")
    public String chatCompletion(@RequestBody String prompt) {
        return chatModel.call(prompt);
    }
}
