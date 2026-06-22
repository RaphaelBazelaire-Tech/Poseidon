package com.nnk.springboot.service;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.repository.CurvePointRepository;
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
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    private final CurvePointMapper curvePointMapper = new CurvePointMapper();
    private CurvePointService curvePointService;

    @BeforeEach
    public void setUp() {
        curvePointService = new CurvePointService(curvePointRepository, curvePointMapper);
    }

    private CurvePointEntity sample() {
        CurvePointEntity entity = new CurvePointEntity();
        entity.setId(1);
        entity.setCurveId(7);
        return entity;
    }

    @Test
    public void findAllReturnsModels() {
        when(curvePointRepository.findAll()).thenReturn(List.of(sample()));
        List<CurvePointModel> result = curvePointService.findAll();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(7), result.getFirst().getCurveId());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void findByIdReturnsModel() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<CurvePointModel> result = curvePointService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(Integer.valueOf(7), result.get().getCurveId());
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    public void savePersistsAndReturnsModel() {
        when(curvePointRepository.save(any(CurvePointEntity.class))).thenReturn(sample());
        CurvePointModel model = CurvePointModel.builder().curveId(7).build();
        CurvePointModel saved = curvePointService.save(model);
        assertEquals(Integer.valueOf(7), saved.getCurveId());
        verify(curvePointRepository, times(1)).save(any(CurvePointEntity.class));
    }

    @Test
    public void deleteByIdShouldDelegate() {
        curvePointService.deleteById(1);
        verify(curvePointRepository, times(1)).deleteById(1);
    }
}
