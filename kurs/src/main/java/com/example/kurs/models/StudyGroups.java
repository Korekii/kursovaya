package com.example.kurs.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "StudyGroups")
public class StudyGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Students> students;

    public StudyGroups(String name) {
        this.name = name;
    }

    public StudyGroups() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }
    public List getStudentsList(){
        return students;
    }
}
