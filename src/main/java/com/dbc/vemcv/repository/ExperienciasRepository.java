package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.ExperienciasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienciasRepository extends JpaRepository<ExperienciasEntity, Integer> {
    @Query("select e from EXPERIENCIAS e " +
            "inner join e.candidato c " +
            "where c.idCandidato = :idCandidato ")
    List<ExperienciasEntity> findByIdCandidato(Integer idCandidato);
}
