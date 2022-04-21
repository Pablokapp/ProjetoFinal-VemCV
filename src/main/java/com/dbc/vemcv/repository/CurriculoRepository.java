package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.CurriculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculoRepository extends JpaRepository<CurriculoEntity, Integer> {
    @Query(value = "select * from curriculo c where fk_candidato = :idCandidato",nativeQuery = true)
    CurriculoEntity getCurriculoByIdCandidato(Integer idCandidato);


}
