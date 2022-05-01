package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.CandidatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepository extends JpaRepository<CandidatoEntity, Integer> {
    boolean existsByCpf(String cpf);

}