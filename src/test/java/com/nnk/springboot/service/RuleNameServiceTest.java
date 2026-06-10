package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repository.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    private RuleName sample() {
        RuleName rule = new RuleName();
        rule.setId(1);
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setSqlStr("SELECT 1");
        rule.setSqlPart("WHERE 1=1");
        return rule;
    }

    @Test
    public void findAllShouldReturnAll() {
        when(ruleNameRepository.findAll()).thenReturn(List.of(sample()));
        List<RuleName> result = ruleNameService.findAll();
        assertEquals(1, result.size());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void findByIdShouldReturnEntity() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<RuleName> result = ruleNameService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Rule Name", result.get().getName());
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    public void saveShouldDelegate() {
        RuleName rule = sample();
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(rule);
        assertEquals(rule, ruleNameService.save(rule));
        verify(ruleNameRepository, times(1)).save(rule);
    }

    @Test
    public void deleteByIdShouldDelegate() {
        ruleNameService.deleteById(1);
        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}
