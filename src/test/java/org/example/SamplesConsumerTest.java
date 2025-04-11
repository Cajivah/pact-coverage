package org.example;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.example.indexing.SamplesClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

@PactConsumerTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Main.class)
@MockServerConfig(port = "8080")
public class SamplesConsumerTest {

    @Autowired
    private SamplesClient samplesClient;


    @Pact(provider = "samples", consumer = "indexing")
    public V4Pact getSamplesPact(PactBuilder builder) {
        return builder
                .expectsToReceiveHttpInteraction("GET /samples", http -> {
                    return http
                            .withRequest(request -> request
                                    .method("GET")
                                    .path("/samples")
                                    .queryParameter("pageToken", "1")
                                    .header("X-Trace-Id", "trace-1")
                            )
                            .willRespondWith(response -> response
                                    .status(200)
                                    .body(newJsonBody(root -> root
                                            .arrayContaining("content", content -> content
                                                    .object(sample -> sample
                                                            .stringType("sampleId", "1")
                                                            .stringType("name", "sample-name")))
                                            .object("metadata", meta -> meta
                                                    .stringType("nextPageToken", "2"))
                                    ).build()));
                }).toPact();
    }

    @Test
    @PactTestFor(providerName = "samples", pactMethod = "getSamplesPact", pactVersion = PactSpecVersion.V4)
    void testGetSamples(MockServer mockServer) throws IOException {
        // Fetch the CSV report
        var allSamples = samplesClient.findAllSamples("1", "trace-1");
        // Verify it is as expected
        assertThat(allSamples.content()).containsExactly(new SamplesClient.IndexedSample("1", "sample-name"));
        assertThat(allSamples.metadata().nextPageToken()).isEqualTo("2");

    }

}
