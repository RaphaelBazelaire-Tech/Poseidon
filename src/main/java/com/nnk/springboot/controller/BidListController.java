package com.nnk.springboot.controller;

import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.service.BidListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BidListController {

    private final BidListService bidListService;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addForm(@ModelAttribute("bidList") BidListModel bidList) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidList") BidListModel bidList,
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.save(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListModel bidList = bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid list Id: " + id));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("bidList") BidListModel bidList,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidList.setBidListId(id);
        bidListService.save(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidListModel bidList = bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid list Id: " + id));
        bidListService.deleteById(bidList.getBidListId());
        return "redirect:/bidList/list";
    }
}
