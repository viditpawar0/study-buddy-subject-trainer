package com.studybuddy.subject_trainer.utils;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class Utils {
    public static <T, ID> T assertEntityExists(ID id, JpaRepository<T, ID> repository) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
