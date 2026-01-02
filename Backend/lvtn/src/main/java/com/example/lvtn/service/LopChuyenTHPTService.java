package com.example.lvtn.service;

import com.example.lvtn.dto.LopChuyenTHPTDTO;
import com.example.lvtn.repository.LopChuyenTHPTRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopChuyenTHPTService {
    private final LopChuyenTHPTRepository lcthptRepository;

    public LopChuyenTHPTService(LopChuyenTHPTRepository lcthptRepository) {
        this.lcthptRepository = lcthptRepository;
    }

    public List<LopChuyenTHPTDTO> getAllLopChuyenTHPTByMaTHPT(String maTHPT) {
        return lcthptRepository.getAllLopChuyenTHPTByMaTHPT(maTHPT);
    }
}
