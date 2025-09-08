package com.studybuddy.subject_trainer.entities;

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
    @Column
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters = new ArrayList<>();
    @Column
    private URL syllabusDocument;
}
