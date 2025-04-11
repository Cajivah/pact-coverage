package org.example.samples;

import org.springframework.stereotype.Repository;

@Repository
public class DummySamplesRepository implements SamplesRepository {

    @Override
    public Page<Sample> findAll(String pageToken, String pageSize) {
        return null;
    }
}
