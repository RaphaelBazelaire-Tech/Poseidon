package com.nnk.springboot.api;

import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingRestController {

    private final RatingService ratingService;

    public RatingRestController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public List<RatingModel> getAll() {
        return ratingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingModel> getById(@PathVariable("id") Integer id) {
        return ratingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RatingModel> create(@Valid @RequestBody RatingModel rating) {
        RatingModel saved = ratingService.save(rating);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingModel> update(@PathVariable("id") Integer id,
                                              @Valid @RequestBody RatingModel rating) {
        if (ratingService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        rating.setId(id);
        return ResponseEntity.ok(ratingService.save(rating));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (ratingService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ratingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
