package com.example.lvtn.repository;

import com.example.lvtn.model.DTUT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DTUTRepository extends JpaRepository<DTUT, Integer> {
}
