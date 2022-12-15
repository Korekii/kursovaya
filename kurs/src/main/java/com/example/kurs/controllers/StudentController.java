package com.example.kurs.controllers;

import com.example.kurs.models.Students;
import com.example.kurs.models.StudyGroups;
import com.example.kurs.repo.StudentsRepository;
import com.example.kurs.repo.StudyGroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private StudyGroupsRepository studyGroupsRepository;

    @GetMapping("/students")
    public String students(Model model) {
        model.addAttribute("title","Список студентов");
        Iterable<Students> students = studentsRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }
    @Transactional
    @GetMapping("/students/{id}")
    public String studentsDetails(@PathVariable(value = "id") long id,
                                  Model model) {
        if (!studentsRepository.existsById(id)) {
            return "redirect:/students";
        }

        Optional<Students> students = studentsRepository.findById(id);
        StudyGroups groups = students.get().getGroup();
        model.addAttribute("name",students.get().getName());
        model.addAttribute("id", students.get().getId());
        model.addAttribute("group_name", groups.getName());
        return "students-details";
    }

    @GetMapping("/students/add")
    public String studentsAdd(Model model) {
        model.addAttribute("title","Добавление студента");

        model.addAttribute("group", studyGroupsRepository.findAll());

        return "students-add";
    }
    @Transactional
    @PostMapping("/students/add")
    public String studentsPostAdd(@RequestParam String name,
                                  @RequestParam String group,
                                  Model model) {
        Students students = new Students(name);
        if(studyGroupsRepository.findByName(group).isPresent()){
            StudyGroups studyGroups = (StudyGroups) studyGroupsRepository.findByName(group).get();
            students.setGroup(studyGroups);
            studentsRepository.save(students);
        }
        else{
            StudyGroups studyGroups = new StudyGroups(group);
            studyGroupsRepository.save(studyGroups);
            students.setGroup(studyGroups);
            studentsRepository.save(students);
        }
        studentsRepository.save(students);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String studentsEdit(@PathVariable(value = "id") long id,
                               Model model) {
        if (!studentsRepository.existsById(id)) {
            return "redirect:/students";
        }
        Optional<Students> students = studentsRepository.findById(id);
        Students student = students.get();
        model.addAttribute("students",student.getName());
        model.addAttribute("group", studyGroupsRepository.findAll());
        return "students-edit";
    }

    @PostMapping("/students/edit/{id}")
    public String studentsPostUpdate(@PathVariable(value = "id") long id,
                                     @RequestParam String name,
                                     @RequestParam String group,
                                     Model model) {
        Students students = studentsRepository.findById(id).orElseThrow();

        if(studyGroupsRepository.findByName(group).isPresent()){
            StudyGroups studyGroups = (StudyGroups) studyGroupsRepository.findByName(group).get();
            students.setName(name);
            students.setGroup(studyGroups);
            studentsRepository.save(students);
        } else {
            StudyGroups studyGroups = new StudyGroups(group);
            studyGroupsRepository.save(studyGroups);
            students.setName(name);
            students.setGroup(studyGroups);
            studentsRepository.save(students);
        }
        return "redirect:/students";
    }

    @PostMapping("/students/remove/{id}")
    public String studentsPostDelete(@PathVariable(value = "id") long id,
                                     Model model) {
        studentsRepository.delete(studentsRepository.findById(id).orElseThrow());
        return "redirect:/students";
    }
}
