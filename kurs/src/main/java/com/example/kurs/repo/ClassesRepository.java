package com.example.kurs.repo;

import com.example.kurs.models.Classes;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ClassesRepository extends CrudRepository<Classes, Long>{
}

