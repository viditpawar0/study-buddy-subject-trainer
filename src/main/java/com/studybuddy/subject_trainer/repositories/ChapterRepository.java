package com.studybuddy.subject_trainer.repositories;

import com.studybuddy.subject_trainer.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Iterable<Chapter> findBySubject_Id(Long subjectId);
}
