package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.CandidatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatoRepository extends JpaRepository<CandidatoEntity, Integer> {
    boolean existsByCpf(String cpf);

}