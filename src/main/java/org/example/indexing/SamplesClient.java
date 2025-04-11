package org.example.indexing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "samples", url = "http://localhost:8080")
public interface SamplesClient {

    record IndexedSample(
            String sampleId,
            String name
    ) {
    }

    record Metadata(String nextPageToken) {
    }

    record Page<T>(Iterable<T> content, Metadata metadata) {
    }

    @GetMapping("/samples")
    Page<IndexedSample> findAllSamples(@RequestParam("pageToken") String pageToken, @RequestHeader("X-Trace-Id") String traceId);
}
