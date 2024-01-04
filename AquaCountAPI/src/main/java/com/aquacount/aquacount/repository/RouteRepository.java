package com.aquacount.aquacount.repository;

import com.aquacount.aquacount.model.measurement.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity,Long> {

    List<RouteEntity> findByCounterid(Long counterid);

}
