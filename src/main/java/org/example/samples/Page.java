package org.example.samples;

public record Page<T>(Iterable<T> content, Metadata metadata) {
    record Metadata(int page, int size, int totalPages, long totalElements) {
    }

}

