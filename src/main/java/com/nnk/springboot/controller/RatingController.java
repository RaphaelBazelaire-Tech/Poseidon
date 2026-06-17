package com.nnk.springboot.controller;

import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addForm(@ModelAttribute("rating") RatingModel rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") RatingModel rating,
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.save(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingModel rating = ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("rating") RatingModel rating,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "rating/update";
        }

        rating.setId(id);
        ratingService.save(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingService.deleteById(id);
        return "redirect:/rating/list";
    }
}
