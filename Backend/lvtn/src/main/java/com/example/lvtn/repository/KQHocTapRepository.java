package com.example.lvtn.repository;

import com.example.lvtn.model.KQHocTap;
import com.example.lvtn.model.KQHocTapID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KQHocTapRepository extends JpaRepository<KQHocTap, KQHocTapID> {

    List<KQHocTap> findById_MaHS(String maHS);

    void deleteById_MaHS(String maHS);
}
