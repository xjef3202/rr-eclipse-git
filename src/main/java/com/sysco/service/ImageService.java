package com.sysco.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ImageService {

    private String imgSvcUrl;

    private RestTemplate restTemplate;

    public ImageService(String imgSvcUrl, RestTemplate restTemplate) {
        this.imgSvcUrl = imgSvcUrl;
        this.restTemplate = restTemplate;
    }

    public String getImageInfo(String supc) {

        String response = null;
        try {
            response = restTemplate.getForObject(imgSvcUrl + supc, String.class);
        } catch (ResourceAccessException ex) {
            log.error("imageServiceException:{}",ex.getMessage());
        }
        return response;
    }
}
