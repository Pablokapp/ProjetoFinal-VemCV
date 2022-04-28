package com.dbc.vemcv.repository;


import com.dbc.vemcv.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByNome(String cargoName);
}
