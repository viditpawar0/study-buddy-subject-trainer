package com.studybuddy.subject_trainer.services;

import com.studybuddy.subject_trainer.entities.Chapter;
import com.studybuddy.subject_trainer.repositories.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChapterService {
    private final ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public Chapter create(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    public Optional<Chapter> retrieve(Long id) {
        return chapterRepository.findById(id);
    }

    public Iterable<Chapter> retrieveBySubjectId(Long subjectId) {
        return chapterRepository.findBySubject_Id(subjectId);
    }

    public Chapter update(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    public void delete(Chapter chapter) {
        chapterRepository.delete(chapter);
    }

    public void deleteById(Long id) {
        chapterRepository.deleteById(id);
    }
}
