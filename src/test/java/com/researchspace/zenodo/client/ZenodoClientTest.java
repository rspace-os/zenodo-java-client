package com.researchspace.zenodo.client;

import com.researchspace.zenodo.model.ZenodoDeposition;
import com.researchspace.zenodo.model.ZenodoFile;
import com.researchspace.zenodo.model.ZenodoSubmission;
import com.researchspace.zenodo.model.RelatedIdentifier;
import com.researchspace.zenodo.model.ControlledVocabularyTerm;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
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
import java.util.Collections;

class ZenodoClientTest {

    private ZenodoClientImpl zenodoClientImpl;
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void startUp() throws MalformedURLException {
        zenodoClientImpl = new ZenodoClientImpl(new URL("https://sandbox.zenodo.org/api"), "<dummy api key");
        mockServer = MockRestServiceServer.createServer(zenodoClientImpl.getRestTemplate());
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
    public void testCreateDepositionWithTitle() throws IOException, URISyntaxException {
        String newDepositionJson = IOUtils.resourceToString("/json/newDeposition.json", Charset.defaultCharset());
        mockServer.expect(requestTo(containsString("https://sandbox.zenodo.org/api/deposit/depositions")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.metadata.title").value("foo"))
                .andExpect(jsonPath("$.metadata.description").value("bar"))
                .andExpect(jsonPath("$.metadata.upload_type").value("other"))
                .andExpect(jsonPath("$.metadata.related_identifiers[0].identifier").value("10.5072/zenodo.1059996"))
                .andExpect(jsonPath("$.metadata.subjects[0].term").value("Astronomy"))
                .andRespond(withSuccess(newDepositionJson, MediaType.APPLICATION_JSON));

        ZenodoSubmission toSubmit = new ZenodoSubmission(
            "foo",
            "bar",
            "other",
            Collections.singletonList(
              new RelatedIdentifier(
                "10.5072/zenodo.1059996",
                "isDocumentedBy",
                "publication-datamanagementplan"
              )
            ),
            Collections.singletonList(
              new ControlledVocabularyTerm(
                "Astronomy",
                new URI("http://id.loc.gov/authorities/subjects/sh85009003"),
                "url"
              )
            )
        );
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

    /**
     * This is real connection test, i.e.  doesn't use mocks, but connects to Zenodo API.
     * To run the test uncomment @Test annotation, to make it pass provide correct apiUrl/token.
     * 
     * @throws IOException
     */
    //@Test
    public void realConnectionTest_createDepositAndUploadFile() throws IOException {
        String apiUrl = "https://sandbox.zenodo.org/api";
        String token = "<your API token>";
        ZenodoClientImpl realZenodoClientImpl = new ZenodoClientImpl(new URL(apiUrl), token);

        // create empty deposition
        ZenodoDeposition deposition = realZenodoClientImpl.createDeposition();
        assertNotNull(deposition);
        assertNotNull(deposition.getId());
        // upload test file 
        File file = new File("src/test/resources/files/example.txt");
        ZenodoFile depositedFile = realZenodoClientImpl.depositFile(deposition, "example.txt", file);
        assertEquals("example.txt", depositedFile.getKey());
        assertEquals("text/plain", depositedFile.getMimetype());
        assertEquals("md5:29b933a8d9a0fcef0af75f1713f4940e", depositedFile.getChecksum());
    }

}
