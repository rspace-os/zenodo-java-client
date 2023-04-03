package com.researchspace.zenodo.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.researchspace.zenodo.model.ZenodoDeposition;
import com.researchspace.zenodo.model.ZenodoFile;
import com.researchspace.zenodo.model.ZenodoSubmission;
import java.util.List;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.containsString;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.nio.charset.Charset;

class ZenodoClientTest {

    private ZenodoClientImpl zenodoClientImpl;
    private MockRestServiceServer mockServer;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void startUp() throws MalformedURLException {
        zenodoClientImpl = new ZenodoClientImpl(new URL("https://sandbox.zenodo.org/api"), "<dummy api key");
        mockServer = MockRestServiceServer.createServer(zenodoClientImpl.getRestTemplate());
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateDeposition() throws IOException {
        String newDepositionJson = IOUtils.resourceToString("/json/newDeposition.json", Charset.defaultCharset());
				mockServer.expect(requestTo(containsString("https://sandbox.zenodo.org/api/deposit/depositions")))
					.andExpect(method(HttpMethod.POST))
          .andRespond(withSuccess(newDepositionJson, MediaType.APPLICATION_JSON));

        ZenodoDeposition deposition = zenodoClientImpl.createDeposition();
        assertNotNull(deposition);
        assertEquals(deposition.getId(), 1175878);
    }

    @Test
    public void testCreateDepositionWithTitle() throws IOException {
        String newDepositionJson = IOUtils.resourceToString("/json/newDeposition.json", Charset.defaultCharset());
				mockServer.expect(requestTo(containsString("https://sandbox.zenodo.org/api/deposit/depositions")))
					.andExpect(method(HttpMethod.POST))
          .andExpect(jsonPath("$.metadata.title").value("foo"))
          .andExpect(jsonPath("$.metadata.description").value("bar"))
          .andExpect(jsonPath("$.metadata.upload_type").value("other"))
          .andRespond(withSuccess(newDepositionJson, MediaType.APPLICATION_JSON));

        ZenodoSubmission toSubmit = new ZenodoSubmission("foo", "bar", "other");
        ZenodoDeposition deposition = zenodoClientImpl.createDeposition(toSubmit);
        assertNotNull(deposition);
        assertEquals(deposition.getId(), 1175878);
    }

    @Test
    public void testGetDepositions() throws IOException {
        String newDepositionJson = IOUtils.resourceToString("/json/newDeposition.json", Charset.defaultCharset());
				mockServer.expect(requestTo(containsString("https://sandbox.zenodo.org/api/deposit/depositions")))
					.andExpect(method(HttpMethod.GET))
          .andRespond(withSuccess("[" + newDepositionJson + "]", MediaType.APPLICATION_JSON));

        List<ZenodoDeposition> depositions = zenodoClientImpl.getDepositions();
        assertNotNull(depositions);
        assertEquals(depositions.size(), 1);
    }

    @Test
    public void testDepositFile() throws IOException {
        String newDepositionJson = IOUtils.resourceToString("/json/newDeposition.json", Charset.defaultCharset());
				mockServer.expect(requestTo(containsString("https://sandbox.zenodo.org/api/deposit/depositions")))
					.andExpect(method(HttpMethod.POST))
          .andRespond(withSuccess(newDepositionJson, MediaType.APPLICATION_JSON));
				mockServer.expect(requestTo(containsString("https://sandbox.zenodo.org/api/files/8b8fe756-3f14-454b-9236-af33ca8d7605")))
					.andExpect(method(HttpMethod.PUT))
          .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        ZenodoDeposition deposition = zenodoClientImpl.createDeposition();
        File file = new File("src/test/resources/files/example.txt");
        ZenodoFile depositedFile = zenodoClientImpl.depositFile(deposition, "example", file);
        assertNotNull(depositedFile);
    }
}
