package com.researchspace.zenodo.client;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.researchspace.zenodo.model.ZenodoSubmission;
import com.researchspace.zenodo.model.ZenodoDeposition;
import com.researchspace.zenodo.model.ZenodoFile;
import java.util.Collections;
import org.springframework.core.ParameterizedTypeReference;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

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
        this.restTemplate = new RestTemplate();
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
