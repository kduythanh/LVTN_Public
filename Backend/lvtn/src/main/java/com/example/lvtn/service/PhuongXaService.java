package com.example.lvtn.service;

import com.example.lvtn.model.PhuongXa;
import com.example.lvtn.repository.PhuongXaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhuongXaService {
    private final PhuongXaRepository pxRepository;
    public PhuongXaService(PhuongXaRepository pxRepository) {
        this.pxRepository = pxRepository;
    }

    public List<PhuongXa> findAllPhuongXa() {
        return pxRepository.findAll();
    }

    public PhuongXa findPhuongXaBymaPhuongXa(int maPhuongXa) {
        return pxRepository.findById(maPhuongXa).orElse(null);
    }
}
