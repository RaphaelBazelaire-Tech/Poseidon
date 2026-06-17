package com.nnk.springboot.service;

import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.repository.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;
    private final RuleNameMapper ruleNameMapper;

    public RuleNameService(RuleNameRepository ruleNameRepository, RuleNameMapper ruleNameMapper) {
        this.ruleNameRepository = ruleNameRepository;
        this.ruleNameMapper = ruleNameMapper;
    }

    public List<RuleNameModel> findAll() {
        return ruleNameRepository.findAll().stream().map(ruleNameMapper::toModel).toList();
    }

    public Optional<RuleNameModel> findById(Integer id) {
        return ruleNameRepository.findById(id).map(ruleNameMapper::toModel);
    }

    public RuleNameModel save(RuleNameModel model) {
        return ruleNameMapper.toModel(ruleNameRepository.save(ruleNameMapper.toEntity(model)));
    }

    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
