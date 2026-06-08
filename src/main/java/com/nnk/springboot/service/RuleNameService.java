package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repository.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    public RuleName save(RuleName entity) {
        return ruleNameRepository.save(entity);
    }

    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
