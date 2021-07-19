package com.testAxmor.issue.controllers;

import com.testAxmor.issue.models.Issue;
import com.testAxmor.issue.repo.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("/issue")
    public String issue(Model model) {
        Iterable<Issue> issues = issueRepository.findAll();
        model.addAttribute("issues", issues);
        return "issue-main";
    }

    @GetMapping("/issue/add")
    public String issueAdd(Model model) {
        return "issue-add";
    }

    @PostMapping("/issue/add")
    public String issuePostAdd(@RequestParam String title, @RequestParam String author, @RequestParam String description, Model model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Issue issue = new Issue(title, author, "Создано", description, dateFormat.format(new Date()));
        issueRepository.save(issue);
        return "redirect:/issue";
    }

    @GetMapping("/issue/{id}")
    public String issueDetails(@PathVariable(value = "id") long id, Model model) {
        if (!issueRepository.existsById(id)) {
            return "redirect:/issue";
        }
        Optional<Issue> issue = issueRepository.findById(id);
        ArrayList<Issue> res = new ArrayList<>();
        issue.ifPresent(res::add);
        model.addAttribute("issue", res);
        return "issue-details";
    }

    @GetMapping("/issue/{id}/edit")
    public String issueEdit(@PathVariable(value = "id") long id, Model model) {
        if (!issueRepository.existsById(id)) {
            return "redirect:/issue";
        }
        Optional<Issue> issue = issueRepository.findById(id);
        ArrayList<Issue> res = new ArrayList<>();
        issue.ifPresent(res::add);
        model.addAttribute("issue", res);
        return "edit";
    }

    @PostMapping("/issue/{id}/edit")
    public String issuePostUpdate(@PathVariable(value = "id") long id, @RequestParam String status, @RequestParam String author, @RequestParam String description, Model model) {
        Issue issue = issueRepository.findById(id).orElseThrow(IllegalStateException::new);
        issue.setAuthor(author);
        issue.setDescription(description);
        issue.setStatus(status);
        issueRepository.save(issue);

        return "redirect:/issue";
    }

    @PostMapping("/issue/{id}/remove")
    public String issuePostDelete(@PathVariable(value = "id") long id, Model model) {
        Issue issue = issueRepository.findById(id).orElseThrow(IllegalStateException::new);
        issueRepository.delete(issue);

        return "redirect:/issue";
    }
}
