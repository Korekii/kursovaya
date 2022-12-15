package com.example.kurs.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Students")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id",referencedColumnName = "id",nullable = false)
    private StudyGroups group;
    private String group_name;
    public Students() {
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name() {
        this.group_name = getGroup().getName();
    }

    public Students(String name) {
        this.name = name;
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

    public StudyGroups getGroup() {
        return group;
    }

    public void setGroup(StudyGroups group) {
        this.group = group;
    }

}
