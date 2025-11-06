package com.studybuddy.subject_trainer.controllers;

import com.studybuddy.subject_trainer.entities.Subject;
import com.studybuddy.subject_trainer.services.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("subjects")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<Iterable<Subject>> post(@RequestBody Iterable<Subject> subject) {
        return ResponseEntity.ofNullable(subjectService.create(subject));
    }

    @PostMapping("auto")
    public ResponseEntity<Subject> post(@RequestPart("name") String name, @RequestPart("syllabusDoc") MultipartFile syllabusDoc) throws IOException {
        return ResponseEntity.ofNullable(subjectService.create(name, syllabusDoc));
    }

    @GetMapping("{id}")
    public ResponseEntity<Subject> get(@PathVariable Long id) {
        return ResponseEntity.of(subjectService.retrieve(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Subject>> getAll() {
        return ResponseEntity.ofNullable(subjectService.retrieveAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<Subject> put(@RequestBody Subject subject, @PathVariable Long id) {
        return ResponseEntity.ofNullable(subjectService.update(subject, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }
}
