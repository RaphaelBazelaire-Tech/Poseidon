package com.nnk.springboot.controller;

import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.service.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RuleNameController {

    private final RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addForm(@ModelAttribute("ruleName") RuleNameModel ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleNameModel ruleName,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameModel ruleName = ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule name Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("ruleName") RuleNameModel ruleName,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule name Id:" + id));
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
