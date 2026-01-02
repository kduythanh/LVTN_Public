package com.example.lvtn.service;

import com.example.lvtn.model.DTUT;
import com.example.lvtn.repository.DTUTRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTUTService {
    private final DTUTRepository dtutRepository;
    public DTUTService(DTUTRepository dtutRepository) {
        this.dtutRepository = dtutRepository;
    }

    public List<DTUT> findAllDTUT() {
        return dtutRepository.findAll();
    }
}
