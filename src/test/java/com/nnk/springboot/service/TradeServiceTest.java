package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;
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
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private Trade sample() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account Test");
        trade.setType("Type Test");
        trade.setBuyQuantity(10d);
        return trade;
    }

    @Test
    public void findAllShouldReturnAll() {
        when(tradeRepository.findAll()).thenReturn(List.of(sample()));
        List<Trade> result = tradeService.findAll();
        assertEquals(1, result.size());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void findByIdShouldReturnEntity() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<Trade> result = tradeService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Account Test", result.get().getAccount());
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    public void saveShouldDelegate() {
        Trade trade = sample();
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
        assertEquals(trade, tradeService.save(trade));
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void deleteByIdShouldDelegate() {
        tradeService.deleteById(1);
        verify(tradeRepository, times(1)).deleteById(1);
    }
}
