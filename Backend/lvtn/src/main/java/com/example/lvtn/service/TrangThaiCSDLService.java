package com.example.lvtn.service;

import com.example.lvtn.model.TrangThaiCSDL;
import com.example.lvtn.repository.TrangThaiCSDLRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class TrangThaiCSDLService {

    private final TrangThaiCSDLRepository ttcsdlRepository;

    public TrangThaiCSDLService(TrangThaiCSDLRepository ttcsdlRepository) {
        this.ttcsdlRepository = ttcsdlRepository;
    }

    @Transactional(readOnly = true)
    public List<TrangThaiCSDL> findAll() {
        return ttcsdlRepository.findAll();
    }

    public TrangThaiCSDL findById(int id) {
        return ttcsdlRepository.findById(id).orElse(null);
    }

    public Boolean isChoPhepCapNhatHoSo() {
        Optional<TrangThaiCSDL> tt = ttcsdlRepository.findById(1);
        return tt.map(TrangThaiCSDL::getGiaTriBoolean).orElse(null);
    }

    public LocalDateTime getThoiHanDangKy() {
        Optional<TrangThaiCSDL> tt = ttcsdlRepository.findById(2);
        return tt.map(TrangThaiCSDL::getGiaTriTimestamp).orElse(null);
    }

    public Boolean isChoPhepCapSoBaoDanh() {
        Optional<TrangThaiCSDL> tt = ttcsdlRepository.findById(3);
        return tt.map(TrangThaiCSDL::getGiaTriBoolean).orElse(null);
    }

    public Boolean isChoPhepCapNhatDiem() {
        Optional<TrangThaiCSDL> tt = ttcsdlRepository.findById(4);
        return tt.map(TrangThaiCSDL::getGiaTriBoolean).orElse(null);
    }

    public Boolean isChoPhepXetTuyen() {
        Optional<TrangThaiCSDL> tt = ttcsdlRepository.findById(5);
        return tt.map(TrangThaiCSDL::getGiaTriBoolean).orElse(null);
    }

    public Boolean isKhoaCongBoKetQua() {
        Optional<TrangThaiCSDL> tt = ttcsdlRepository.findById(6);
        return tt.map(TrangThaiCSDL::getGiaTriBoolean).orElse(null);
    }

    public LocalDateTime getThoiGianCongBoKetQua() {
        Optional<TrangThaiCSDL> tt = ttcsdlRepository.findById(7);
        return tt.map(TrangThaiCSDL::getGiaTriTimestamp).orElse(null);
    }

    @Transactional
    public void updateTrangThai(Integer maTT, String kieuDuLieu, String newVal) {
        TrangThaiCSDL tt = ttcsdlRepository.findById(maTT)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy trạng thái"));
        switch (kieuDuLieu) {
            case "BOOLEAN" -> {
                boolean boolVal = Boolean.parseBoolean(newVal);
                tt.setGiaTriBoolean(boolVal);
            }
            case "TIMESTAMP" -> {
                Instant instant = Instant.parse(newVal);
                LocalDateTime local = instant.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
                tt.setGiaTriTimestamp(local);
            }
            case "STRING" -> tt.setGiaTriChuoi(newVal);
        }
        tt.setTgCapNhat(LocalDateTime.now());
        ttcsdlRepository.save(tt);
    }
}
