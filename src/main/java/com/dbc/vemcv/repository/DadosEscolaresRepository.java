package com.dbc.vemcv.repository;


import com.dbc.vemcv.entity.DadosEscolaresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosEscolaresRepository extends JpaRepository<DadosEscolaresEntity, Integer> {

}
