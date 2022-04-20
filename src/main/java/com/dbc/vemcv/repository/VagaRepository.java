package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.VagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {
    boolean existsById(Integer id);
}
