package com.nnk.springboot.api;

import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.service.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/curvePoint")
public class CurvePointRestController {

    private final CurvePointService curvePointService;

    public CurvePointRestController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @GetMapping
    public List<CurvePointModel> getAll() {
        return curvePointService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurvePointModel> getById(@PathVariable Integer id) {
        return curvePointService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CurvePointModel> create(@Valid @RequestBody CurvePointModel curvePoint) {
        CurvePointModel saved = curvePointService.save(curvePoint);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurvePointModel> update(@PathVariable("id") Integer id,
                                                  @Valid @RequestBody CurvePointModel curvePoint) {
        if (curvePointService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        curvePoint.setId(id);
        return ResponseEntity.ok(curvePointService.save(curvePoint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (curvePointService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        curvePointService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
