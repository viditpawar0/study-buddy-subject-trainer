package com.studybuddy.subject_trainer.entities;

import com.studybuddy.subject_trainer.entity_listeners.ChapterEntityListener;
import jakarta.persistence.*;
import lombok.Data;

@EntityListeners(ChapterEntityListener.class)
@Entity
@Data
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    private String chapterNumber;

    @Column
    private String name;

    @Column
    private Status status;

    @ManyToOne
    private Subject subject;
}
