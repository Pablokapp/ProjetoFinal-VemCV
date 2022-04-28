package com.dbc.vemcv.repository;


import com.dbc.vemcv.entity.DadosEscolaresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DadosEscolaresRepository extends JpaRepository<DadosEscolaresEntity, Integer> {
    @Query("select de from DADOS_ESCOLARES de " +
            "inner join de.candidato c " +
            "where c.idCandidato = :idCandidato ")
    List<DadosEscolaresEntity> findByIdCandidato(Integer idCandidato);

}
