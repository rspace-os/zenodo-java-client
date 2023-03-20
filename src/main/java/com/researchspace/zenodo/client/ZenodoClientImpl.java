package com.researchspace.zenodo.client;

import lombok.Getter;
import lombok.Setter;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        this.restTemplate.setRequestFactory(requestFactory);

    }

    @Override
    public boolean testConnection() {
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrlBase + "/test", HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), String.class);
            log.debug("Test connection response: {}", response.getBody());
            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            log.error("Error testing connection to Zenodo API: " + e.getMessage());
            return false;
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Type", "application/json");
        return headers;
    }

}
