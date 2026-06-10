package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repository.BidListRepository;
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
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    private BidList sample() {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account Test");
        bidList.setType("Type Test");
        bidList.setBidQuantity(10d);
        return bidList;
    }

    @Test
    public void findAllShouldReturnAll() {
        when(bidListRepository.findAll()).thenReturn(List.of(sample()));
        List<BidList> result = bidListService.findAll();
        assertEquals(1, result.size());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void findByIdShouldReturnEntity() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<BidList> result = bidListService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Account Test", result.get().getAccount());
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    public void saveShouldDelegate() {
        BidList bidList = sample();
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);
        assertEquals(bidList, bidListService.save(bidList));
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    public void deleteByIdShouldDelegate() {
        bidListService.deleteById(1);
        verify(bidListRepository, times(1)).deleteById(1);
    }
}
