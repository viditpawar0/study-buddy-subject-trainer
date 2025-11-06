package com.studybuddy.subject_trainer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subject")
    @JsonIgnore
    private List<Chapter> chapters = new ArrayList<>();

    @Column
    private URL syllabusDocument;

    @Column
    private String syllabusDocS3Key;

    @Column
    private Status status;

    @Column
    private String uid;

    public void addChapters(Chapter... chapters) {
        for (final var chapter : chapters) {
            chapter.setSubject(this);
            this.chapters.add(chapter);
        }
    }

    public void removeChapters(Chapter... chapters) {
        for (final var chapter : chapters){
            this.chapters.remove(chapter);
        }
    }

    public void clearChapters() {
        this.chapters.clear();
    }
}
