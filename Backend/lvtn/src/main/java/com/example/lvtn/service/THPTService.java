package com.example.lvtn.service;

import com.example.lvtn.dto.ChiTieuTHPTChuyenDTO;
import com.example.lvtn.dto.ChiTieuTHPTDTO;
import com.example.lvtn.model.THPT;
import com.example.lvtn.repository.THPTRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class THPTService {
    private final THPTRepository thptRepository;
    public THPTService(THPTRepository THPTRepository) {
        this.thptRepository = THPTRepository;
    }

    public List<THPT> findAllTHPT() {
        return thptRepository.findAll();
    }

    @Transactional
    public List<ChiTieuTHPTDTO> findDSTHPTAndChiTieu() {
        return thptRepository.findDSTHPTAndChiTieu();
    }

    @Transactional
    public List<ChiTieuTHPTChuyenDTO> findDSTHPTChuyenAndChiTieu() {
        return thptRepository.findDSTHPTChuyenAndChiTieu();
    }
}
