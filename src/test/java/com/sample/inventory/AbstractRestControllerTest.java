package com.sample.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public abstract class AbstractRestControllerTest extends AbstractTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String postCheckStatusAndReturnBody(String url, Object criteria, HttpStatus status) {
        return postCheckStatusAndReturnBody(url, toRequest(criteria), status);
    }

    protected String postCheckStatusAndReturnBody(String url, HttpEntity<?> request, HttpStatus status) {

        ResponseEntity<String> responseEntity =
                this.restTemplate.exchange(url, POST, request, String.class);

        assertThat(responseEntity,
                hasProperty("statusCode", is(status))
        );

        return responseEntity.getBody();
    }

    protected String getCheckStatusAndReturnBody(String url, HttpStatus status) {

        ResponseEntity<String> responseEntity =
                this.restTemplate.exchange(url, GET, null, String.class);

        assertThat(responseEntity,
                hasProperty("statusCode", is(status))
        );

        return responseEntity.getBody();
    }

    protected String putCheckStatusAndReturnBody(String url, Object criteria, HttpStatus status) {
        return putCheckStatusAndReturnBody(url, toRequest(criteria), status);
    }

    protected String putCheckStatusAndReturnBody(String url, HttpEntity<?> request, HttpStatus status) {

        ResponseEntity<String> responseEntity =
                this.restTemplate.exchange(url, PUT, request, String.class);

        assertThat(responseEntity,
                hasProperty("statusCode", is(status))
        );

        return responseEntity.getBody();
    }

    protected String deleteCheckStatusAndReturnBody(String url, HttpStatus status) {

        ResponseEntity<String> responseEntity =
                this.restTemplate.exchange(url, DELETE, null, String.class);

        assertThat(responseEntity,
                hasProperty("statusCode", is(status))
        );

        return responseEntity.getBody();
    }

    // private

    public <T> HttpEntity<T> toRequest(T body) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(APPLICATION_JSON);

        return new HttpEntity<>(body, headers);
    }
}
