package com.studybuddy.subject_trainer.services;

import com.studybuddy.subject_trainer.entities.Chapter;
import com.studybuddy.subject_trainer.repositories.ChapterRepository;
import com.studybuddy.subject_trainer.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final SubjectService subjectService;

    public ChapterService(ChapterRepository chapterRepository, SubjectService subjectService) {
        this.chapterRepository = chapterRepository;
        this.subjectService = subjectService;
    }

    public Iterable<Chapter> create(Long subjectId, Iterable<Chapter> chapters) {
        chapters.forEach(chapter -> chapter.setSubject(subjectService.assertEntityExists(subjectId)));
        return chapterRepository.saveAll(chapters);
    }

    public Optional<Chapter> retrieve(Long id) {
        return chapterRepository.findById(id);
    }

    public Iterable<Chapter> retrieveBySubjectId(Long subjectId) {
        subjectService.assertEntityExists(subjectId);
        return chapterRepository.findBySubject_Id(subjectId);
    }

    public Chapter update(Chapter newChapter, Long id) {
        final var oldChapter = assertEntityExists(id);
        if (newChapter.getChapterNumber() != null) {
            oldChapter.setChapterNumber(newChapter.getChapterNumber());
        }
        if (newChapter.getName() != null) {
            oldChapter.setName(newChapter.getName());
        }
        return chapterRepository.save(oldChapter);
    }

    public void delete(Chapter chapter) {
        assertEntityExists(chapter.getId());
        chapterRepository.delete(chapter);
    }

    public void delete(Long id) {
        assertEntityExists(id);
        chapterRepository.deleteById(id);
    }

    public Chapter assertEntityExists(Long chapterId) {
        return Utils.assertEntityExists(chapterId, chapterRepository);
    }
}
