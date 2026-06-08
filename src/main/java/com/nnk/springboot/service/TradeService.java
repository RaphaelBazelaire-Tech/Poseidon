package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> findById(Integer id) {
        return tradeRepository.findById(id);
    }

    public Trade save(Trade entity) {
        return tradeRepository.save(entity);
    }

    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}
