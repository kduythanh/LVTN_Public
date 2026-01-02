package com.example.lvtn.service;

import com.example.lvtn.model.DanToc;
import com.example.lvtn.repository.DanTocRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanTocService {
    private final DanTocRepository dtRepository;
    public DanTocService(DanTocRepository repository) {
        this.dtRepository = repository;
    }

    // Lấy danh sách các dân tộc (dùng trong dropdown)
    public List<DanToc> findAllDanToc() {
        return dtRepository.findAll();
    }
}
