package com.nnk.springboot.service;

import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.model.TradeModel;
import com.nnk.springboot.repository.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    public TradeService(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    public List<TradeModel> findAll() {
        return tradeRepository.findAll().stream().map(tradeMapper::toModel).toList();
    }

    public Optional<TradeModel> findById(Integer id) {
        return tradeRepository.findById(id).map(tradeMapper::toModel);
    }

    public TradeModel save(TradeModel model) {
        return tradeMapper.toModel(tradeRepository.save(tradeMapper.toEntity(model)));
    }

    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}
