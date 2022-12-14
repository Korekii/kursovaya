package com.example.kurs.controllers;

import com.example.kurs.models.Students;
import com.example.kurs.repo.StudentsRepository;
import com.example.kurs.models.StudyGroups;
import com.example.kurs.repo.StudyGroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SiteController {
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private StudyGroupsRepository studyGroupsRepository;

    @GetMapping("/students")
    public String students(Model model) {
        Iterable<Students> students = studentsRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/students/add")
    public String studentsAdd(Model model) {
        return "students-add";
    }

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

    @GetMapping("/students/{id}")
    public String studentsDetails(@PathVariable(value = "id") long id,
                                  Model model) {
        if (!studentsRepository.existsById(id)) {
            return "redirect:/students";
        }
        Optional<Students> students = studentsRepository.findById(id);
        ArrayList<Students> res = new ArrayList<>();
        students.ifPresent(res::add);
        model.addAttribute("students", res);
        return "students-details";
    }

    @GetMapping("/students/{id}/edit")
    public String studentsEdit(@PathVariable(value = "id") long id,
                               Model model) {
        if (!studentsRepository.existsById(id)) {
            return "redirect:/students";
        }
        Optional<Students> students = studentsRepository.findById(id);
        ArrayList<Students> res = new ArrayList<>();
        students.ifPresent(res::add);
        model.addAttribute("students", res);
        return "students-edit";
    }

    @PostMapping("/students/{id}/edit")
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

    @PostMapping("/students/{id}/remove")
    public String studentsPostDelete(@PathVariable(value = "id") long id,
                                     Model model) {
        Students students = studentsRepository.findById(id).orElseThrow();
        studentsRepository.delete(students);
        return "redirect:/students";
    }

    @GetMapping("/groups")
    public String groups(Model model) {
        Iterable <StudyGroups> studyGroups = studyGroupsRepository.findAll();
        model.addAttribute("studyGroups", studyGroups);
        return "groups";
    }

    @GetMapping("/groups/add")
    public String groupsAdd(Model model) {
        return "groups-add";
    }

    @PostMapping("/groups/add")
    public String groupsPostAdd(@RequestParam String name,
                                Model model) {
        if(studyGroupsRepository.findByName(name).isPresent()){
            return "redirect:/groups";
        }
        StudyGroups studyGroups = new StudyGroups(name);
        studyGroupsRepository.save(studyGroups);
        return "redirect:/groups";
    }

    @GetMapping("/groups/{id}")
    public String groupsEdit(@PathVariable(value = "id") long id,
                                Model model) {
        if (!studyGroupsRepository.existsById(id)) {
            return "redirect:/groups";
        }
        Optional<StudyGroups> studyGroups = studyGroupsRepository.findById(id);
        ArrayList<StudyGroups> res = new ArrayList<>();
        studyGroups.ifPresent(res::add);
        model.addAttribute("studyGroups", res);
        return "groups-details";
    }
    @GetMapping("/groups/{id}/edit")
    public String groupsDetails(@PathVariable(value = "id") long id,
                                Model model) {
        if (!studyGroupsRepository.existsById(id)) {
            return "redirect:/groups";
        }
        Optional<StudyGroups> studyGroups = studyGroupsRepository.findById(id);
        ArrayList<StudyGroups> res = new ArrayList<>();
        studyGroups.ifPresent(res::add);
        model.addAttribute("studyGroups", res);
        return "groups-edit";
    }

    @PostMapping("/groups/{id}/edit")
    public String groupsPostUpdate(@PathVariable(value = "id") long id,
                                   @RequestParam String name,
                                   Model model) {
        StudyGroups studyGroups = studyGroupsRepository.findById(id).orElseThrow();
        studyGroups.setName(name);
        studyGroupsRepository.save(studyGroups);
        return "redirect:/groups";
    }
}
