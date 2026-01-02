package com.example.lvtn.service;

import com.example.lvtn.model.NguyenVong;
import com.example.lvtn.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NguyenVongService {
    private final NguyenVongRepository nvRepository;

    public NguyenVongService(NguyenVongRepository nvRepository) {
        this.nvRepository = nvRepository;
    }

    @Transactional
    public List<NguyenVong> findAll() {
        return nvRepository.findAll();
    }
}
