package com.studybuddy.subject_trainer.services;

import com.studybuddy.subject_trainer.entities.Status;
import com.studybuddy.subject_trainer.entities.Subject;
import com.studybuddy.subject_trainer.repositories.SubjectRepository;
import com.studybuddy.subject_trainer.utils.Utils;
import io.awspring.cloud.s3.S3Template;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final S3Template s3Template;
    private final SubjectInitiationService subjectInitiationService;

    public SubjectService(SubjectRepository subjectRepository,
                          S3Template s3Template,
                          @Lazy SubjectInitiationService subjectInitiationService) {
        this.subjectRepository = subjectRepository;
        this.s3Template = s3Template;
        this.subjectInitiationService = subjectInitiationService;
    }

    public Iterable<Subject> create(Iterable<Subject> subjects) {
        subjects.forEach(subject -> subject.setStatus(Status.INITIALIZED));
        return subjectRepository.saveAll(subjects);
    }

    public Subject create(String name, MultipartFile syllabus) throws IOException {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setSyllabusDocS3Key(UUID.randomUUID().toString());
        subject.setSyllabusDocument(s3Template.upload(
                "study-buddy-bucket-s3",
                subject.getSyllabusDocS3Key(),
                syllabus.getInputStream()
        ).getURL());
        subject.setStatus(Status.INITIALIZING);
        Subject saved = subjectRepository.save(subject);
        subjectInitiationService.initializeSubjectAsync(saved, syllabus);
        return saved;
    }

    public Optional<Subject> retrieve(Long id){
        return subjectRepository.findById(id);
    }

    public Iterable<Subject> retrieveAll() {
        return subjectRepository.findAll();
    }

    public Subject update(Subject newSubject, Long id) {
        final var oldSubject = assertEntityExists(id);
        if (newSubject.getName() != null) {
            oldSubject.setName(newSubject.getName());
        }
        if (newSubject.getStatus() != null) {
            oldSubject.setStatus(newSubject.getStatus());
        }
        return subjectRepository.save(oldSubject);
    }

    public void delete(Subject subject) {
        assertEntityExists(subject.getId());
        subjectRepository.delete(subject);
    }

    public void delete(Long id) {
        assertEntityExists(id);
        subjectRepository.deleteById(id);
    }

    public Subject assertEntityExists(Long subjectId) {
        return Utils.assertEntityExists(subjectId, subjectRepository);
    }
}
