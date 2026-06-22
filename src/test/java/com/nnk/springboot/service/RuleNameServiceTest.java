package com.nnk.springboot.service;

import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.repository.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private final RuleNameMapper ruleNameMapper = new RuleNameMapper();
    private RuleNameService ruleNameService;

    @BeforeEach
    public void setUp() {
        ruleNameService = new RuleNameService(ruleNameRepository, ruleNameMapper);
    }

    private RuleNameEntity sample() {
        RuleNameEntity entity = new RuleNameEntity();
        entity.setId(1);
        entity.setName("name_v");
        return entity;
    }

    @Test
    public void findAllReturnsModels() {
        when(ruleNameRepository.findAll()).thenReturn(List.of(sample()));
        List<RuleNameModel> result = ruleNameService.findAll();
        assertEquals(1, result.size());
        assertEquals("name_v", result.getFirst().getName());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void findByIdReturnsModel() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<RuleNameModel> result = ruleNameService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("name_v", result.get().getName());
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    public void savePersistsAndReturnsModel() {
        when(ruleNameRepository.save(any(RuleNameEntity.class))).thenReturn(sample());
        RuleNameModel model = RuleNameModel.builder().name("name_v").build();
        RuleNameModel saved = ruleNameService.save(model);
        assertEquals("name_v", saved.getName());
        verify(ruleNameRepository, times(1)).save(any(RuleNameEntity.class));
    }

    @Test
    public void deleteByIdShouldDelegate() {
        ruleNameService.deleteById(1);
        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}
