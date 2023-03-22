package com.researchspace.zenodo.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.researchspace.zenodo.model.ZenodoDeposition;
import com.researchspace.zenodo.model.ZenodoFile;
import com.researchspace.zenodo.model.ZenodoSubmission;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void testCreateDeposition() throws IOException {
        // String submissionRequestJson = IOUtils.resourceToString("/zenodoSubmissionRequest.json", Charset.defaultCharset());
        // ZenodoSubmission toSubmit = objectMapper.readValue(submissionRequestJson, ZenodoSubmission.class);
        ZenodoSubmission toSubmit = new ZenodoSubmission();
        ZenodoDeposition submissionResponse = zenodoClientImpl.createDeposition(toSubmit);
        assertNotNull(submissionResponse);
    }

    @Test
    public void testDepositFile() throws IOException {
        ZenodoDeposition deposition = zenodoClientImpl.createDeposition(new ZenodoSubmission());
        File file = new File("src/test/resources/files/example.txt");
        ZenodoFile depositedFile = zenodoClientImpl.depositFile(deposition, "example", file);
        assertNotNull(depositedFile);
    }
}
