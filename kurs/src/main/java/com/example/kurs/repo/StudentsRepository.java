package com.example.kurs.repo;

import com.example.kurs.models.Students;
import org.springframework.data.repository.CrudRepository;


public interface StudentsRepository extends CrudRepository<Students, Long> {
    Iterable<Students> findByGroup(Long group_id);

}

