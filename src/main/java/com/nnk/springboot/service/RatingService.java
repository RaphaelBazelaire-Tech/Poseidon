package com.nnk.springboot.service;

import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    public List<RatingModel> findAll() {
        return ratingRepository.findAll().stream().map(ratingMapper::toModel).toList();
    }

    public Optional<RatingModel> findById(Integer id) {
        return ratingRepository.findById(id).map(ratingMapper::toModel);
    }

    public RatingModel save(RatingModel model) {
        return ratingMapper.toModel(ratingRepository.save(ratingMapper.toEntity(model)));
    }

    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
}
