package com.nnk.springboot.api;

import com.nnk.springboot.model.TradeModel;
import com.nnk.springboot.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class TradeRestController {

    private final TradeService tradeService;

    public TradeRestController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping
    public List<TradeModel> getAll() {
        return tradeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeModel> getById(@PathVariable("id") Integer id) {
        return tradeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TradeModel> create(@Valid @RequestBody TradeModel trade) {
        TradeModel saved = tradeService.save(trade);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getTradeId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TradeModel> update(@PathVariable("id") Integer id,
                                             @Valid @RequestBody TradeModel trade) {
        if (tradeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        trade.setTradeId(id);
        return ResponseEntity.ok(tradeService.save(trade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (tradeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        tradeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
