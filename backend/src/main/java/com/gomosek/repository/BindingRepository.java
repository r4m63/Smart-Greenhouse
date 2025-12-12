package com.gomosek.repository;

import com.gomosek.entity.BindingEntity;
import com.gomosek.entity.ModuleEntity;
import com.gomosek.entity.PortEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BindingRepository extends JpaRepository<BindingEntity, Long> {
    Optional<BindingEntity> findByPort(PortEntity port);

    Optional<BindingEntity> findByModuleAndPort_PortId(ModuleEntity module, Integer portId);

    List<BindingEntity> findByModule(ModuleEntity module);

    void deleteByModule(ModuleEntity module);
}
