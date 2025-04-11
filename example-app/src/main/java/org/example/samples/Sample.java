package org.example.samples;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record Sample(
        String sampleId,
        String sampleType,
        String name,
        BigDecimal price,
        List<Tag> tags,
        Instant createdAt
) {
    public record Tag(String name, Instant addedAt) {
    }
}
