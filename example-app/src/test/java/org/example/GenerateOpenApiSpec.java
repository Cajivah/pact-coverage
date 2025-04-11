package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenerateOpenApiSpec {

    @LocalServerPort
    private int port;

    @Test
    void getOpenApiFile() {
        var getApiDocsRequest = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:%s/v3/api-docs".formatted(port))).build();
        try (HttpClient client = HttpClient.newHttpClient()) {
            var response = client.send(getApiDocsRequest, HttpResponse.BodyHandlers.ofString());
            try (var writer = new BufferedWriter(new FileWriter("build/open-api.json"))) {
                writer.write(response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
