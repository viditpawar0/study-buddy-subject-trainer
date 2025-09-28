package com.studybuddy.subject_trainer.controllers;

import com.studybuddy.subject_trainer.entities.Chapter;
import com.studybuddy.subject_trainer.services.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chapters")
public class ChapterController {
    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @PostMapping
    public ResponseEntity<Chapter> post(Chapter chapter) {
        return ResponseEntity.ofNullable(chapterService.create(chapter));
    }

    @GetMapping("{id}")
    public ResponseEntity<Chapter> get(@PathVariable Long id) {
        return ResponseEntity.of(chapterService.retrieve(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Chapter>> getBySubjectId(@RequestParam Long subjectId) {
        return ResponseEntity.ofNullable(chapterService.retrieveBySubjectId(subjectId));
    }

    @PutMapping
    public ResponseEntity<Chapter> put(Chapter chapter) {
        return ResponseEntity.ofNullable(chapterService.update(chapter));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody Chapter chapter) {
        chapterService.delete(chapter);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        chapterService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
