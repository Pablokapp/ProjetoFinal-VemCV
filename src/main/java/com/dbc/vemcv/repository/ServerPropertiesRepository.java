package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.VagaEntity;
import com.dbc.vemcv.model.ServerProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerPropertiesRepository extends JpaRepository<ServerProperties, Integer> {
}
