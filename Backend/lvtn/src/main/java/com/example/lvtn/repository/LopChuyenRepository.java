package com.example.lvtn.repository;

import com.example.lvtn.model.LopChuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LopChuyenRepository extends JpaRepository<LopChuyen, String> {
}
