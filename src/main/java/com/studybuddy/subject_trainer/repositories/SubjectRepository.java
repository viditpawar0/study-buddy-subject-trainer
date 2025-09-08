package com.studybuddy.subject_trainer.repositories;

import com.studybuddy.subject_trainer.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
