package com.researchspace.zenodo.client;

import com.researchspace.zenodo.model.ZenodoDeposition;
import com.researchspace.zenodo.model.ZenodoSubmission;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.client.MockRestServiceServer;
import java.net.MalformedURLException;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.net.URL;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.URISyntaxException;

class ZenodoClientTest {

    private ZenodoClientImpl zenodoClientImpl;
    private MockRestServiceServer mockServer;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void startUp() throws MalformedURLException {
        zenodoClientImpl = new ZenodoClientImpl(new URL("https://sandbox.zenodo.org/api"), "<dummy api key");
        // mockServer = MockRestServiceServer.createServer(zenodoClientImpl.getRestTemplate());
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateDeposition() throws IOException, URISyntaxException {
        // String submissionRequestJson = IOUtils.resourceToString("/zenodoSubmissionRequest.json", Charset.defaultCharset());
        // ZenodoSubmission toSubmit = objectMapper.readValue(submissionRequestJson, ZenodoSubmission.class);
        ZenodoSubmission toSubmit = new ZenodoSubmission();
        ZenodoDeposition submissionResponse = zenodoClientImpl.createDeposition(toSubmit);
        assertNotNull(submissionResponse);
    }
}
