package com.example.lvtn.repository;

import com.example.lvtn.model.DTKKChuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DTKKChuyenRepository extends JpaRepository<DTKKChuyen, Integer> {
}
