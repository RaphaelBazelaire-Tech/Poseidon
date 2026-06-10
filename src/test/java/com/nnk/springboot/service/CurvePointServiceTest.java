package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repository.CurvePointRepository;
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
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    private CurvePoint sample() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(5d);
        curvePoint.setValue(15d);
        return curvePoint;
    }

    @Test
    public void findAllShouldReturnAll() {
        when(curvePointRepository.findAll()).thenReturn(List.of(sample()));
        List<CurvePoint> result = curvePointService.findAll();
        assertEquals(1, result.size());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void findByIdShouldReturnEntity() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<CurvePoint> result = curvePointService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(10, result.get().getCurveId());
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    public void saveShouldDelegate() {
        CurvePoint curvePoint = sample();
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
        assertEquals(curvePoint, curvePointService.save(curvePoint));
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    public void deleteByIdShouldDelegate() {
        curvePointService.deleteById(1);
        verify(curvePointRepository, times(1)).deleteById(1);
    }
}
