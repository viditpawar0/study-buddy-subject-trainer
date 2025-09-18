package com.studybuddy.subject_trainer.controllers;

import com.studybuddy.subject_trainer.entities.Subject;
import com.studybuddy.subject_trainer.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("subject")
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Subject> post(@RequestPart("subject") String name, @RequestPart("syllabus") MultipartFile syllabus) throws IOException {
        return ResponseEntity.ofNullable(subjectService.create(name, syllabus));
    }

    @GetMapping
    public ResponseEntity<Iterable<Subject>> getAll() {
        return ResponseEntity.ofNullable(subjectService.retrieveAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Subject> get(@PathVariable Long id) {
        return ResponseEntity.of(subjectService.retrieve(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }
}
