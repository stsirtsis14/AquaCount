package com.aquacount.aquacount.repository;

import com.aquacount.aquacount.model.measurement.entity.MeasurementEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MeasurementRepository extends JpaRepository<MeasurementEntity,Long> {
    List<MeasurementEntity> findByClockid(Long id);


    @Query("SELECT m FROM MeasurementEntity m " +
            "JOIN ClockEntity c ON m.clockid = c.clockid " +
            "JOIN RouteEntity r ON c.routeid = r.routeid " +
            "JOIN CountersEntity cnt ON r.counterid = cnt.counterid " +
            "WHERE cnt.counterid = :counterid")
    List<MeasurementEntity> findByCounterid(Long counterid);

}
