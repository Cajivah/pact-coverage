package org.example.samples;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SamplesController {

    private final SamplesRepository samplesRepository;

    public SamplesController(SamplesRepository samplesRepository) {
        this.samplesRepository = samplesRepository;
    }

    @GetMapping("/samples")
    public Page<Sample> samples(
            @RequestParam("pageToken") String pageToken,
            @RequestParam("pageSize") String pageSize
    ) {
        return samplesRepository.findAll(pageToken, pageSize);
    }

}
