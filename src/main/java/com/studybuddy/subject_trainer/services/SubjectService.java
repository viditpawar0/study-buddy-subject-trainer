package com.studybuddy.subject_trainer.services;

import com.studybuddy.subject_trainer.entities.Chapter;
import com.studybuddy.subject_trainer.entities.Subject;
import com.studybuddy.subject_trainer.feign_clients.TopicDTO;
import com.studybuddy.subject_trainer.feign_clients.TopicFeignClient;
import com.studybuddy.subject_trainer.repositories.SubjectRepository;
import io.awspring.cloud.s3.S3Template;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final S3Template s3Template;
    private final ChatModel chatModel;
    private final ChatClient chatClient;
    private final TopicFeignClient topicFeignClient;

    public SubjectService(SubjectRepository subjectRepository, S3Template s3Template, ChatModel chatModel, TopicFeignClient topicFeignClient) {
        this.subjectRepository = subjectRepository;
        this.s3Template = s3Template;
        this.chatModel = chatModel;
        this.chatClient = ChatClient.create(chatModel);
        this.topicFeignClient = topicFeignClient;
    }

    public Subject create(String name, MultipartFile syllabus) throws IOException {
        // Phase 0: create the Subject
        Subject subject = new Subject();
        subject.setName(name);
        subject.setSyllabusDocument(
                s3Template.upload("study-buddy", UUID.randomUUID().toString(), syllabus.getInputStream()).getURL()
        );

        // Extract text from PDF
        var pdfText = new StringBuilder();
        for (var document : new PagePdfDocumentReader(syllabus.getResource()).get()) {
            pdfText.append(document.getText());
        }

//         Call AI to extract chapters + topics
        ChaptersAIResponseFormat extracted = chatClient
                .prompt()
                .system("Extract units/chapters and topics from the provided text. Strictly stick to the contents of the file.")
                .user(pdfText.toString())
                .call()
                .entity(ChaptersAIResponseFormat.class);
        // Temporary hardcoded response until AI part is fixed
//        ChaptersAIResponseFormat extracted = new ChaptersAIResponseFormat(
//                List.of(
//                        new ChapterDTO("1", "Introduction", List.of("What is X?", "History of X")),
//                        new ChapterDTO("2", "Advanced Concepts", List.of("Deep Dive into Y", "Applications of Y"))
//                )
//        );

        // Phase 1: add Chapters without topics
        for (ChapterDTO chapterDTO : extracted.chapters()) {
            Chapter chapter = new Chapter();
            chapter.setName(chapterDTO.name());
            chapter.setChapterNumber(chapterDTO.chapterNumber());
            subject.getChapters().add(chapter);
        }

        // Save Subject + Chapters (IDs are now generated)
        Subject saved = subjectRepository.save(subject);

        // Phase 2: post topics to topic service using chapter IDs
        List<Chapter> persistedChapters = saved.getChapters();
        for (int i = 0; i < persistedChapters.size(); i++) {
            Chapter chapter = persistedChapters.get(i);
            List<String> topics = extracted.chapters().get(i).topics();
            for (String topic : topics) {
                System.out.println("Posting topic: " + topic + " for chapter ID: " + chapter.getId());
                topicFeignClient.postTopic(new TopicDTO(chapter.getId(), topic));
            }
        }

        return saved;
    }


    private record ChapterDTO(String chapterNumber, String name, List<String> topics){}
    private record ChaptersAIResponseFormat(List<ChapterDTO> chapters){}

    public Optional<Subject> retrieve(Long id){
        return subjectRepository.findById(id);
    }

    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}
