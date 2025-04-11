package org.example.samples;

import java.util.Collection;

public record Page<T>(Collection<T> content, Metadata metadata) {
    record Metadata(int page, int size, int totalPages, long totalElements) {
    }
}

