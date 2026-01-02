package com.example.lvtn.service;

import com.example.lvtn.model.DTKK;
import com.example.lvtn.repository.DTKKRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTKKService {
    private final DTKKRepository dtkkRepository;
    public DTKKService(DTKKRepository dtkkRepository) {
        this.dtkkRepository = dtkkRepository;
    }

    // Lấy danh sách các đối tượng khuyến khích (dùng trong dropdown)
    public List<DTKK> findAllDTKK() {
        return dtkkRepository.findAll();
    }
}
