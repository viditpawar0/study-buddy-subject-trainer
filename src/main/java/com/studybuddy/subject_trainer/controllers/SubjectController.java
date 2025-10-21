package com.studybuddy.subject_trainer.controllers;

import com.studybuddy.subject_trainer.entities.Subject;
import com.studybuddy.subject_trainer.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("subjects")
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @PostMapping
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
