package com.example.kurs.repo;

import com.example.kurs.models.StudyGroups;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudyGroupsRepository extends CrudRepository<StudyGroups, Long> {

    Optional<Object> findByName(String group);
}

