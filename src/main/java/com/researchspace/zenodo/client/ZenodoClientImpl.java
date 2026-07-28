package com.researchspace.zenodo.client;

import com.researchspace.zenodo.model.ZenodoDeposition;
import com.researchspace.zenodo.model.ZenodoFile;
import com.researchspace.zenodo.model.ZenodoSubmission;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@Slf4j
public class ZenodoClientImpl implements ZenodoClient {

    private URL apiUrlBase;
    private String token;
    private RestTemplate restTemplate;

    public ZenodoClientImpl(URL apiUrlBase, String token) {
        this.apiUrlBase = apiUrlBase;
        this.token = token;
        // Buffer request bodies so JSON POSTs carry a Content-Length header. Spring 6.1+
        // streams bodies of unknown length as chunked, which Zenodo's proxy rejects with 502.
        this.restTemplate = new RestTemplate(
            new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    }

    /*
     * Create a new deposition
     */

    @Override
    public ZenodoDeposition createDeposition() throws IOException {
        return restTemplate.postForEntity(
            this.apiUrlBase + "/deposit/depositions",
            new HttpEntity<>("{}", getHttpHeaders()),
            ZenodoDeposition.class
        ).getBody();
    }

    @Data
    @AllArgsConstructor
    private class ZenodoMetadataRequest {
      private ZenodoSubmission metadata;
    }

    @Override
    public ZenodoDeposition createDeposition(ZenodoSubmission submission) throws IOException {
        return restTemplate.postForEntity(
            this.apiUrlBase + "/deposit/depositions",
            new HttpEntity<>(new ZenodoMetadataRequest(submission), getHttpHeaders()),
            ZenodoDeposition.class
        ).getBody();
    }

    /*
     * Fetch existing Depositions.
     */

    @Override
    public List<ZenodoDeposition> getDepositions() throws IOException {
        return restTemplate.exchange(
            this.apiUrlBase + "/deposit/depositions",
            HttpMethod.GET,
            new HttpEntity<>(getHttpHeaders()),
            new ParameterizedTypeReference<List<ZenodoDeposition>>() {}
        ).getBody();
    }

    /*
     * Deposit a file into an existing deposition
     */

    @Override
    public ZenodoFile depositFile(ZenodoDeposition deposition, String filename, File file) throws IOException {
        HttpHeaders headers = getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return restTemplate.exchange(
            deposition.getBucketURL() + "/" + urlEncode(filename),
            HttpMethod.PUT,
            new HttpEntity<>(new FileSystemResource(file), headers),
            ZenodoFile.class
        ).getBody();
    }

    /*
     * Common methods for all API calls
     */

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + this.token);
        return headers;
    }

    private String urlEncode(String string) {
        return URLEncoder.encode(string, StandardCharsets.UTF_8);
    }
}
