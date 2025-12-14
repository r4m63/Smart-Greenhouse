package com.gomosek.dto;

import com.gomosek.entity.BindingEntity;
import com.gomosek.entity.DriverEntity;
import com.gomosek.entity.MeasurementEntity;
import com.gomosek.entity.ModuleEntity;
import com.gomosek.entity.PortEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class DtoMapper {

    private DtoMapper() {
    }

    public static ModuleSummaryDto toSummary(ModuleEntity module) {
        return new ModuleSummaryDto(
                module.getId(),
                module.getModuleUid(),
                module.getName(),
                module.getBaseUrl(),
                module.getLastSeen()
        );
    }

    public static ModuleDetailsDto toDetails(ModuleEntity module,
                                             List<PortEntity> ports,
                                             List<DriverEntity> drivers,
                                             List<BindingEntity> bindings) {
        List<PortDto> portDtos = ports.stream()
                .map(DtoMapper::toPort)
                .collect(Collectors.toList());
        List<DriverDto> driverDtos = drivers.stream()
                .map(DtoMapper::toDriver)
                .collect(Collectors.toList());
        List<BindingDto> bindingDtos = bindings.stream()
                .map(DtoMapper::toBinding)
                .collect(Collectors.toList());

        return new ModuleDetailsDto(
                module.getId(),
                module.getModuleUid(),
                module.getName(),
                module.getBaseUrl(),
                module.getLastSeen(),
                portDtos,
                driverDtos,
                bindingDtos
        );
    }

    public static PortDto toPort(PortEntity port) {
        return new PortDto(port.getPortId(), port.getType());
    }

    public static DriverDto toDriver(DriverEntity driver) {
        return new DriverDto(driver.getDriverId(), driver.getName(), driver.getType());
    }

    public static BindingDto toBinding(BindingEntity binding) {
        return new BindingDto(
                binding.getPort().getPortId(),
                binding.getDriver().getDriverId(),
                binding.getDriver().getName(),
                binding.getCreatedAt()
        );
    }

    public static MeasurementDto toMeasurement(MeasurementEntity measurement) {
        return new MeasurementDto(
                measurement.getPort().getPortId(),
                measurement.getDriver() != null ? measurement.getDriver().getDriverId() : null,
                measurement.getValue(),
                measurement.getCreatedAt()
        );
    }
}
