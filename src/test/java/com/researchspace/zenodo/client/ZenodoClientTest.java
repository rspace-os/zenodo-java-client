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
import static org.hamcrest.Matchers.containsString;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

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
        // TODO: mock API call and return json object with id
        ZenodoDeposition submissionResponse = zenodoClientImpl.createDeposition();
        assertNotNull(submissionResponse);
        //TODO: assert correct id
    }

    @Test
    public void testCreateDepositionWithTitle() throws IOException {
				// mockServer.expect(requestTo(containsString("https://sandbox.zenodo.org/api/deposit/depositions")))
				// 	.andExpect(method(HttpMethod.POST))
          // .andExpect(jsonPath("$.metadata.title").value("foo"))
          // .andExpect(jsonPath("$.metadata.description").value("bar"))
          // .andExpect(jsonPath("$.metadata.upload_type").value("other"))
          // .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON)); //TODO: return json object with id
        ZenodoSubmission toSubmit = new ZenodoSubmission("foo", "bar", "other");
        ZenodoDeposition submissionResponse = zenodoClientImpl.createDeposition(toSubmit);
        assertNotNull(submissionResponse);
        //TODO: assert correct id
    }

    @Test
    public void testDepositFile() throws IOException {
        ZenodoDeposition deposition = zenodoClientImpl.createDeposition();
        File file = new File("src/test/resources/files/example.txt");
        ZenodoFile depositedFile = zenodoClientImpl.depositFile(deposition, "example", file);
        assertNotNull(depositedFile);
    }
}
