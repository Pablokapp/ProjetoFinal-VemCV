package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.CurriculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculoRepository extends JpaRepository<CurriculoEntity, Integer> {

}
