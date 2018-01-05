package com.sysco.service;

import com.sysco.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private String adminServiceUrl;

    private RestTemplate restTemplate;

    public CustomerService(String adminServiceUrl, RestTemplate restTemplate) {
        this.adminServiceUrl = adminServiceUrl;
        this.restTemplate = restTemplate;
    }

    public Customer getCustomer(String opco, String customerId) {
        return restTemplate.getForEntity(String.join("/", adminServiceUrl, "customers", opco, customerId), Customer.class).getBody();
    }
}
