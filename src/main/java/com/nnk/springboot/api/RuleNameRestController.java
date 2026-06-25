package com.nnk.springboot.api;

import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.service.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rulename")
public class RuleNameRestController {

    private final RuleNameService ruleNameService;

    public RuleNameRestController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @GetMapping
    public List<RuleNameModel> getAll() {
        return ruleNameService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleNameModel> getById(@PathVariable("id") Integer id) {
        return ruleNameService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RuleNameModel> create(@Valid @RequestBody RuleNameModel ruleName) {
        RuleNameModel saved = ruleNameService.save(ruleName);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuleNameModel> update(@PathVariable("id") Integer id,
                                                @Valid @RequestBody RuleNameModel ruleName) {
        if (ruleNameService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ruleName.setId(id);
        return ResponseEntity.ok(ruleNameService.save(ruleName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (ruleNameService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ruleNameService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
