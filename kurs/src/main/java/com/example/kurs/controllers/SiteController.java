package com.example.kurs.controllers;

import com.example.kurs.models.Classes;
import com.example.kurs.repo.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SiteController {

    @Autowired
    private ClassesRepository classesRepository;

    @GetMapping("/studentList")//отображение страницы со студентами
    public String baseStudent(org.springframework.ui.Model model) {
        model.addAttribute("title", "Список студентов");
        Iterable<Classes> classes = classesRepository.findAll();
        model.addAttribute("nameSt", classes);
        return "studentList";
    }

    @GetMapping("/groupList")//отображение страницы с группами
    public String baseGroup(org.springframework.ui.Model model) {
        model.addAttribute("title", "Список групп");
        Iterable<Classes> classes = classesRepository.findAll();
        model.addAttribute("groupSt", classes);
        return "groupList";
    }

    @PostMapping("/studentList")//добавление нового студента
    public String studentAdd(@RequestParam String nameSt, @RequestParam String groupSt, org.springframework.ui.Model model) {
        Classes newStudent = new Classes(nameSt, groupSt);
        classesRepository.save(newStudent);
        return "redirect:/studentList";
    }

    @PostMapping("/groupList")//добавление новой группы
    public String groupAdd(@RequestParam String groupSt, org.springframework.ui.Model model) {
        Classes newGroup = new Classes(groupSt);
        classesRepository.save(newGroup);
        return "redirect:/groupList";
    }

    @GetMapping("/groupList/{id}")//расширенный список группы
    public String detailedList(@PathVariable(value = "id")long id, org.springframework.ui.Model model) {
        if (!classesRepository.existsById(id)){
            return "redirect:/groupList";
        }
        Optional<Classes> thisGroup = classesRepository.findById(id);
        ArrayList<Classes> res = new ArrayList<>();
        thisGroup.ifPresent(res::add);
        model.addAttribute("groupSt", res);
        return "groupDetailed";
    }

    @GetMapping("/groupList/{id}/edit")//изменение группы
    public String groupEdit(@PathVariable(value = "id")long id, org.springframework.ui.Model model) {
        if (!classesRepository.existsById(id)){
            return "redirect:/groupList";
        }
        Optional<Classes> thisGroup = classesRepository.findById(id);
        ArrayList<Classes> res = new ArrayList<>();
        thisGroup.ifPresent(res::add);
        model.addAttribute("groupSt", res);
        return "editGroup";
    }

    @PostMapping("/groupList/{id}/edit")//обновление данных
    public String groupUpdate(@PathVariable(value = "id")long id, @RequestParam String groupSt, org.springframework.ui.Model model) {
        Classes changeGroup = classesRepository.findById(id).orElseThrow();
        changeGroup.setGroupName(groupSt);
        classesRepository.save(changeGroup);
        return "redirect:/groupList";
    }

    @PostMapping("/groupList/{id}/remove")//удаление группы
    public String groupDelete(@PathVariable(value = "id") long id, org.springframework.ui.Model model) {
        Classes deleteGroup = classesRepository.findById(id).orElseThrow();
        classesRepository.delete(deleteGroup);
        return "redirect:/groupList";
    }

    @GetMapping("/studentList/{id}/edit")//изменение данных студента
    public String studentEdit(@PathVariable(value = "id") long id, org.springframework.ui.Model model) {
        if (!classesRepository.existsById(id)){
            return "redirect:/studentList";
        }
        Optional<Classes> thisStudent = classesRepository.findById(id);
        ArrayList<Classes> res = new ArrayList<>();
        thisStudent.ifPresent(res::add);
        model.addAttribute("nameSt", res);
        return "editStudent";
    }

    @PostMapping("/studentList/{id}/edit")//обновление данных
    public String studentUpdate(@PathVariable(value = "id")long id, @RequestParam String nameSt, @RequestParam String groupSt, org.springframework.ui.Model model) {
        Classes changeStudent = classesRepository.findById(id).orElseThrow();
        changeStudent.setStudentName(nameSt);
        changeStudent.setGroupName(groupSt);
        classesRepository.save(changeStudent);
        return "redirect:/studentList";
    }

    @PostMapping("/studentList/{id}/remove")//удаление данных студента
    public String studentDelete(@PathVariable(value = "id") long id, org.springframework.ui.Model model) {
        Classes deleteStudent = classesRepository.findById(id).orElseThrow();
        classesRepository.delete(deleteStudent);
        return "redirect:/studentList";
    }


}
