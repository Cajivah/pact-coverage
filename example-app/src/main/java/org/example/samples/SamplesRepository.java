package org.example.samples;

public interface SamplesRepository {

    Page<Sample> findAll(String pageToken, String pageSize);
}
