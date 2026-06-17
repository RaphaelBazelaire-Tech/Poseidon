package com.nnk.springboot.service;

import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.repository.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    private final BidListRepository bidListRepository;
    private final BidListMapper bidListMapper;

    public BidListService(BidListRepository bidListRepository, BidListMapper bidListMapper) {
        this.bidListRepository = bidListRepository;
        this.bidListMapper = bidListMapper;
    }

    public List<BidListModel> findAll() {
        return bidListRepository.findAll().stream().map(bidListMapper::toModel).toList();
    }

    public Optional<BidListModel> findById(Integer id) {
        return bidListRepository.findById(id).map(bidListMapper::toModel);
    }

    public BidListModel save(BidListModel model) {
        return bidListMapper.toModel(bidListRepository.save(bidListMapper.toEntity(model)));
    }

    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
