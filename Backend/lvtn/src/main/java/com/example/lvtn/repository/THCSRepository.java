package com.example.lvtn.repository;

import com.example.lvtn.model.THCS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface THCSRepository extends JpaRepository<THCS, String> {
}
