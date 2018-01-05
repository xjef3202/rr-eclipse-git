package com.sysco.utils;


import lombok.NoArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@NoArgsConstructor
public class MockRestTemplate extends RestTemplate {

    public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException {
        return null;
    }
}

