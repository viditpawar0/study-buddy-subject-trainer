package com.studybuddy.subject_trainer.services;

import com.studybuddy.subject_trainer.entities.Chapter;
import com.studybuddy.subject_trainer.entities.Status;
import com.studybuddy.subject_trainer.entities.Subject;
import com.studybuddy.subject_trainer.feign_clients.TopicDTO;
import com.studybuddy.subject_trainer.feign_clients.TopicFeignClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectInitiationService {
    private final ChatClient chatClient;
    private final SubjectService subjectService;
    private final TopicFeignClient topicFeignClient;
    private final ChapterService chapterService;

    public SubjectInitiationService(ChatClient chatClient, SubjectService subjectService, TopicFeignClient topicFeignClient, ChapterService chapterService) {
        this.chatClient = chatClient;
        this.subjectService = subjectService;
        this.topicFeignClient = topicFeignClient;
        this.chapterService = chapterService;
    }

    @Async
    public void initializeSubjectAsync(Subject saved) {
        try {
            final var pdfText = new StringBuilder();
            for (final var document : new PagePdfDocumentReader(saved.getSyllabusDocument().toString()).get()) {
                pdfText.append(document.getText());
            }
            final var systemPrompt = "Extract units/chapters and topics from the provided text. Strictly stick to the contents of the file.";
            final var extracted = chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(pdfText.toString())
                    .call()
                    .entity(ChaptersAIResponseFormat.class);

            //AI Response Stub
//            final var extracted = new ChaptersAIResponseFormat(
//                    new ChapterDTO("1", "Maths",
//                            "Addition", "Subtraction", "Multiplication", "Division"),
//                    new ChapterDTO("2", "Physics",
//                            "Gravity", "Inertia", "Pressure", "Hydraulics"),
//                    new ChapterDTO("3", "Chemistry",
//                            "Molecules", "Particles"),
//                    new ChapterDTO("4", "Biology",
//                            "Humans", "Plants")
//            );

            for (final var chapterDTO : extracted.chapters()) {
                var chapter = new Chapter();
                chapter.setName(chapterDTO.name());
                chapter.setChapterNumber(chapterDTO.chapterNumber());
                chapter = chapterService.create(saved.getId(), List.of(chapter)).iterator().next();
                saved.addChapters(chapter);
                final var topics = new ArrayList<TopicDTO>();
                for(final var topic : chapterDTO.topics) {
                    topics.add(new TopicDTO(topic));
                }
                topicFeignClient.post(topics, chapter.getId());
            }
            saved.setStatus(Status.INITIALIZED);
        } catch (Exception e) {
            saved.setStatus(Status.FAILED);
            throw new RuntimeException(e);
        } finally {
            subjectService.update(saved, saved.getId());
        }
    }

    private record ChapterDTO(String chapterNumber, String name, String... topics){}
    private record ChaptersAIResponseFormat(ChapterDTO... chapters){}
}
