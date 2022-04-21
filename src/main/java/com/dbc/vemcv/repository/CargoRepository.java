package com.dbc.vemcv.repository;


import com.dbc.vemcv.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
    CargoEntity findByNome(String cargoName);
}
