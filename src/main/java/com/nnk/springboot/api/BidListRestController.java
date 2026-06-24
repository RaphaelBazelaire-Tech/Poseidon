package com.nnk.springboot.api;

import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.service.BidListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bidlist")
public class BidListRestController {

    private final BidListService bidListService;

    public BidListRestController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping
    public List<BidListModel> getAll() {
        return bidListService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BidListModel> getById(@PathVariable("id") Integer id) {
        return bidListService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BidListModel> create(@Valid @RequestBody BidListModel bidList) {
        BidListModel saved = bidListService.save(bidList);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getBidListId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BidListModel> update(@PathVariable("id") Integer id,
                                               @Valid @RequestBody BidListModel bidList) {

        if (bidListService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bidList.setBidListId(id);
        return ResponseEntity.ok(bidListService.save(bidList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (bidListService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        bidListService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
