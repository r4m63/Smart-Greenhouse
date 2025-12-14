package com.gomosek.service;

import com.gomosek.client.EspClient;
import com.gomosek.client.dto.BindResponse;
import com.gomosek.client.dto.DriversResponse;
import com.gomosek.client.dto.InfoResponse;
import com.gomosek.client.dto.PortsResponse;
import com.gomosek.client.dto.ReadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;

/**
 * Узко сфокусированный сервис для общения с ESP-модулями.
 * Используется бизнес-логикой как единая точка вызова удаленного API.
 */
@Service
public class EspModuleService {

    private final EspClient espClient;

    public EspModuleService(EspClient espClient) {
        this.espClient = espClient;
    }

    public InfoResponse getInfo(String baseUrl) throws HttpStatusCodeException {
        return espClient.getInfo(baseUrl);
    }

    public PortsResponse getPorts(String baseUrl) throws HttpStatusCodeException {
        return espClient.getPorts(baseUrl);
    }

    public DriversResponse getDrivers(String baseUrl) throws HttpStatusCodeException {
        return espClient.getDrivers(baseUrl);
    }

    public Optional<BindResponse> getBinding(String baseUrl, int portId) throws HttpStatusCodeException {
        return espClient.getBind(baseUrl, portId);
    }

    public void bind(String baseUrl, int portId, int driverId) throws HttpStatusCodeException {
        espClient.bind(baseUrl, portId, driverId);
    }

    public ReadResponse read(String baseUrl, int portId) throws HttpStatusCodeException {
        return espClient.readValue(baseUrl, portId);
    }

    public void write(String baseUrl, int portId, int level) throws HttpStatusCodeException {
        espClient.writeValue(baseUrl, portId, level);
    }
}
