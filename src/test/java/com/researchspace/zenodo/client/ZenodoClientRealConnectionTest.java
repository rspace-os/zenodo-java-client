package com.researchspace.zenodo.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.researchspace.zenodo.model.ZenodoDeposition;
import com.researchspace.zenodo.model.ZenodoFile;
import com.researchspace.zenodo.model.ZenodoSubmission;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * These are real connection test, i.e.  doesn't use mocks, but connects to Zenodo API.
 * To run the tests provide correct apiUrl/token, then uncomment @Test annotations
 */
class ZenodoClientRealConnectionTest {

    private static final String zenodoApiUrl = "https://sandbox.zenodo.org/api";
    private static final String zenodoApiKey = "<your test api key>";

    private ZenodoClientImpl zenodoClientImpl;

    @BeforeEach
    public void startUp() throws MalformedURLException {
        zenodoClientImpl = new ZenodoClientImpl(new URL(zenodoApiUrl), zenodoApiKey);
    }

    //@Test
    public void realConnectionTest_createNamedDepositWithoutFile() throws IOException {
        // create named deposition
        ZenodoSubmission testSubmission = new ZenodoSubmission();
        testSubmission.setTitle("test submission from zenodo-java-client " + Instant.now().toString());
        testSubmission.setDescription("check https://github.com/rspace-os/zenodo-java-client");
        ZenodoDeposition deposition = zenodoClientImpl.createDeposition(testSubmission);
        assertEquals(testSubmission.getTitle(), deposition.getTitle());
        assertEquals("false", deposition.getSubmitted());
    }

    //@Test
    public void realConnectionTest_createUnnamedDepositWithFile() throws IOException {
        // create empty deposition
        ZenodoDeposition deposition = zenodoClientImpl.createDeposition();
        assertNotNull(deposition);
        assertTrue(deposition.getId() > 0);

        // upload test file 
        File file = new File("src/test/resources/files/example.txt");
        ZenodoFile depositedFile = zenodoClientImpl.depositFile(deposition, "example.txt", file);
        assertEquals("example.txt", depositedFile.getKey());
        assertEquals("text/plain", depositedFile.getMimetype());
        assertEquals("md5:bea8252ff4e80f41719ea13cdf007273", depositedFile.getChecksum());
    }

}
