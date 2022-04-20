package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.ExperienciasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienciasRepository extends JpaRepository<ExperienciasEntity, Integer> {
}
