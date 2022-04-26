package com.dbc.vemcv.repository;

import com.dbc.vemcv.entity.VagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {
    boolean existsById(Integer id);

    @Query("select v from VAGA v where v.status = :status1 or v.status = :status2")
    Page<VagaEntity> findByStatus1OrStatus2(String status1, String status2, Pageable pageable);

}
