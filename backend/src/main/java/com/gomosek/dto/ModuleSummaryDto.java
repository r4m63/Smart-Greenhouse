package com.gomosek.dto;

import java.time.Instant;

public record ModuleSummaryDto(
        Long id,
        Integer moduleUid,
        String name,
        String baseUrl,
        Instant lastSeen
) {
}
