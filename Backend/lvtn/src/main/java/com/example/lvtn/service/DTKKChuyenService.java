package com.example.lvtn.service;

import com.example.lvtn.model.DTKKChuyen;
import com.example.lvtn.repository.DTKKChuyenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTKKChuyenService {
    private final DTKKChuyenRepository dtkkchuyenRepository;
    public DTKKChuyenService(DTKKChuyenRepository dtkkchuyenRepository) {
        this.dtkkchuyenRepository = dtkkchuyenRepository;
    }

    public List<DTKKChuyen> findAllDTKKChuyen() {
        return dtkkchuyenRepository.findAll();
    }
}
