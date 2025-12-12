package com.gomosek.repository;

import com.gomosek.entity.MeasurementEntity;
import com.gomosek.entity.ModuleEntity;
import com.gomosek.entity.PortEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {
    List<MeasurementEntity> findByModule(ModuleEntity module);

    Optional<MeasurementEntity> findFirstByModuleAndPortOrderByCreatedAtDesc(ModuleEntity module, PortEntity port);
}
