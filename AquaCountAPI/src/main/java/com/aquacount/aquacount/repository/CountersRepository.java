package com.aquacount.aquacount.repository;

import com.aquacount.aquacount.model.measurement.dto.FindCounter;
import com.aquacount.aquacount.model.measurement.entity.CountersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CountersRepository extends JpaRepository<CountersEntity,Long> {

    CountersEntity findByUsername(String username);

    @Query("SELECT new com.aquacount.aquacount.model.measurement.dto.FindCounter(c.counterid, c.firstName, c.lastName, c.username, c.authority) FROM CountersEntity c INNER JOIN RouteEntity r ON c.counterid = r.counterid WHERE r.routeid = :routeid")
    FindCounter findByRouteid(Long routeid);
}
