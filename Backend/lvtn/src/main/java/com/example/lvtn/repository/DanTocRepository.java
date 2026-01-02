package com.example.lvtn.repository;

import com.example.lvtn.model.DanToc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanTocRepository extends JpaRepository<DanToc, Integer> {
}
