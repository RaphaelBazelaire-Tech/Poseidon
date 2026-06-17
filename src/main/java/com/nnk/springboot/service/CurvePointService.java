package com.nnk.springboot.service;

import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.repository.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;
    private final CurvePointMapper curvePointMapper;

    public CurvePointService(CurvePointRepository curvePointRepository, CurvePointMapper curvePointMapper) {
        this.curvePointRepository = curvePointRepository;
        this.curvePointMapper = curvePointMapper;
    }

    public List<CurvePointModel> findAll() {
        return curvePointRepository.findAll().stream().map(curvePointMapper::toModel).toList();
    }

    public Optional<CurvePointModel> findById(Integer id) {
        return curvePointRepository.findById(id).map(curvePointMapper::toModel);
    }

    public CurvePointModel save(CurvePointModel model) {
        return curvePointMapper.toModel(curvePointRepository.save(curvePointMapper.toEntity(model)));
    }

    public void deleteById(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
