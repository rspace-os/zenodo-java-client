package com.researchspace.zenodo.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.client.MockRestServiceServer;
import java.net.MalformedURLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.net.URL;

class ZenodoClientTest {

    private ZenodoClientImpl zenodoClientImpl;
    private MockRestServiceServer mockServer;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void startUp() throws MalformedURLException {
        zenodoClientImpl = new ZenodoClientImpl(new URL("https://sandbox.zenodo.org/api"), "token");
        mockServer = MockRestServiceServer.createServer(zenodoClientImpl.getRestTemplate());
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() {
    }

}
