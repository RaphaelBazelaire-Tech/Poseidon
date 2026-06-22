package com.nnk.springboot.service;

import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.repository.BidListRepository;
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
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    private final BidListMapper bidListMapper = new BidListMapper();
    private BidListService bidListService;

    @BeforeEach
    public void setUp() {
        bidListService = new BidListService(bidListRepository, bidListMapper);
    }

    private BidListEntity sample() {
        BidListEntity entity = new BidListEntity();
        entity.setBidListId(1);
        entity.setAccount("account_v");
        return entity;
    }

    @Test
    public void findAllReturnsModels() {
        when(bidListRepository.findAll()).thenReturn(List.of(sample()));
        List<BidListModel> result = bidListService.findAll();
        assertEquals(1, result.size());
        assertEquals("account_v", result.getFirst().getAccount());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void findByIdReturnsModel() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<BidListModel> result = bidListService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("account_v", result.get().getAccount());
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    public void savePersistsAndReturnsModel() {
        when(bidListRepository.save(any(BidListEntity.class))).thenReturn(sample());
        BidListModel model = BidListModel.builder().account("account_v").build();
        BidListModel saved = bidListService.save(model);
        assertEquals("account_v", saved.getAccount());
        verify(bidListRepository, times(1)).save(any(BidListEntity.class));
    }

    @Test
    public void deleteByIdShouldDelegate() {
        bidListService.deleteById(1);
        verify(bidListRepository, times(1)).deleteById(1);
    }
}
