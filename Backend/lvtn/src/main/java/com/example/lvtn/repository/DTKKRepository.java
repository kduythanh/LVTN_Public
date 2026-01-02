package com.example.lvtn.repository;

import com.example.lvtn.model.DTKK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DTKKRepository extends JpaRepository<DTKK, Integer> {
}
