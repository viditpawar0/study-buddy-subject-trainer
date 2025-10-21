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
    public ResponseEntity<Iterable<Chapter>> post(@RequestBody Iterable<Chapter> chapters, @RequestParam Long subjectId) {
        return ResponseEntity.ofNullable(chapterService.create(subjectId, chapters));
    }

    @GetMapping("{id}")
    public ResponseEntity<Chapter> get(@PathVariable Long id) {
        return ResponseEntity.of(chapterService.retrieve(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Chapter>> getBySubjectId(@RequestParam Long subjectId) {
        return ResponseEntity.ofNullable(chapterService.retrieveBySubjectId(subjectId));
    }

    @PutMapping("{id}")
    public ResponseEntity<Chapter> put(@RequestBody Chapter chapter, @PathVariable Long id) {
        return ResponseEntity.ofNullable(chapterService.update(chapter, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        chapterService.delete(id);
        return ResponseEntity.ok().build();
    }
}
