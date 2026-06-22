package com.nnk.springboot.service;

import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.model.TradeModel;
import com.nnk.springboot.repository.TradeRepository;
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
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    private final TradeMapper tradeMapper = new TradeMapper();
    private TradeService tradeService;

    @BeforeEach
    public void setUp() {
        tradeService = new TradeService(tradeRepository, tradeMapper);
    }

    private TradeEntity sample() {
        TradeEntity entity = new TradeEntity();
        entity.setTradeId(1);
        entity.setAccount("account_v");
        return entity;
    }

    @Test
    public void findAllReturnsModels() {
        when(tradeRepository.findAll()).thenReturn(List.of(sample()));
        List<TradeModel> result = tradeService.findAll();
        assertEquals(1, result.size());
        assertEquals("account_v", result.getFirst().getAccount());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void findByIdReturnsModel() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<TradeModel> result = tradeService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("account_v", result.get().getAccount());
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    public void savePersistsAndReturnsModel() {
        when(tradeRepository.save(any(TradeEntity.class))).thenReturn(sample());
        TradeModel model = TradeModel.builder().account("account_v").build();
        TradeModel saved = tradeService.save(model);
        assertEquals("account_v", saved.getAccount());
        verify(tradeRepository, times(1)).save(any(TradeEntity.class));
    }

    @Test
    public void deleteByIdShouldDelegate() {
        tradeService.deleteById(1);
        verify(tradeRepository, times(1)).deleteById(1);
    }
}
