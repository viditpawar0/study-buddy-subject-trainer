package com.studybuddy.subject_trainer.repositories;

import com.studybuddy.subject_trainer.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findBySubject_Id(Long subjectId);
}
