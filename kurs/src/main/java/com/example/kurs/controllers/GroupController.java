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

import java.util.List;
import java.util.Optional;

@Controller
public class GroupController {
    @Autowired
    private StudyGroupsRepository studyGroupsRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @GetMapping("/groups")
    public String groups(Model model) {
        Iterable <StudyGroups> studyGroups = studyGroupsRepository.findAll();
        model.addAttribute("title","Список групп");
        model.addAttribute("studyGroups", studyGroups);
        return "groups";
    }
    @Transactional
    @GetMapping("/groups/{id}")
    public String groupsEdit(@PathVariable(value = "id") long id,
                             Model model) {
        if (!studyGroupsRepository.existsById(id)) {
            return "redirect:/groups";
        }
        Optional<StudyGroups> studyGroups = studyGroupsRepository.findById(id);
        StudyGroups groups = studyGroups.get();

        if(groups.getStudents().size() > 0) {
            model.addAttribute("students", groups.getStudents());
        }

        model.addAttribute("name", groups.getName());
        model.addAttribute("id", groups.getId());

        return "groups-details";
    }
    @GetMapping("/groups/edit/{id}")
    public String groupsDetails(@PathVariable(value = "id") long id,
                                Model model) {
        if (!studyGroupsRepository.existsById(id)) {
            return "redirect:/groups";
        }
        Optional<StudyGroups> studyGroups = studyGroupsRepository.findById(id);
        StudyGroups groups = studyGroups.get();
        model.addAttribute("studyGroups",groups.getName());
        return "groups-edit";
    }

    @PostMapping("/groups/edit/{id}")
    public String groupsPostUpdate(@PathVariable(value = "id") long id,
                                   @RequestParam String name,
                                   Model model) {
        StudyGroups studyGroups = studyGroupsRepository.findById(id).orElseThrow();
        studyGroups.setName(name);
        studyGroupsRepository.save(studyGroups);
        return "redirect:/groups";
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
    @PostMapping("/groups/remove/{id}")
    public String groupsRemove(@PathVariable(value = "id") long id,
                               Model model) {
        studyGroupsRepository.delete(studyGroupsRepository.findById(id).orElseThrow());
        return "redirect:/groups";
    }

}
