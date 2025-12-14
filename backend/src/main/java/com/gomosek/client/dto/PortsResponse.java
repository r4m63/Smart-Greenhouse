package com.gomosek.client.dto;

import java.util.List;

public class PortsResponse {

    private List<EspPort> ports;

    public List<EspPort> getPorts() {
        return ports;
    }

    public void setPorts(List<EspPort> ports) {
        this.ports = ports;
    }
}
