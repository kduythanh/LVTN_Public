package com.example.lvtn.service;

import com.example.lvtn.model.THCS;
import com.example.lvtn.repository.THCSRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class THCSService {
    private final THCSRepository thcsRepository;
    public THCSService(THCSRepository thcsRepository) {
        this.thcsRepository = thcsRepository;
    }

    public List<THCS> findAllTHCS() {
        return thcsRepository.findAll();
    }
}
