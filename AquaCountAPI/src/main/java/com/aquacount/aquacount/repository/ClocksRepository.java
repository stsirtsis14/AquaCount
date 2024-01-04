package com.aquacount.aquacount.repository;


import com.aquacount.aquacount.model.measurement.dto.RegisterClockId;
import com.aquacount.aquacount.model.measurement.entity.ClockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClocksRepository extends JpaRepository<ClockEntity,Long> {

    @Query(value = "SELECT c.clockid FROM ClockEntity c WHERE c.routeid = :routeid")
    List<Long> findByRouteid(Long routeid);

    @Query(value = "SELECT c.coordinates FROM ClockEntity c WHERE c.routeid = :routeid")
    List<String> findCoordinatesByRouteid(Long routeid);

}
