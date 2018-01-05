package com.sysco.service;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.sysco.TestUtils;
import com.sysco.model.Customer;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class CustomerServiceTest {


    private RestTemplate restTemplate = mock(RestTemplate.class);

    private CustomerService customerService = new CustomerService(null, restTemplate);

    @Ignore
    @Test
    public void should_get_customer() throws Exception{
        Customer body = TestUtils.readJsonToObject(Customer.class, "customer_response.json");
        ResponseEntity<Customer> response = new ResponseEntity<>(body, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(Customer.class))).thenReturn(response);

        Customer customer = customerService.getCustomer("010", "403030");

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(TestUtils.readFileToString("customer_response.json"));

        assertEquals(customer.getCutoffTime(), JsonPath.read(document, "$.cutoffTime"));
        assertEquals(Integer.valueOf(customer.getDaysInAdvance()), JsonPath.read(document, "$.daysInAdvance"));
        assertEquals(customer.getDesignatedDeliveryDays(), JsonPath.read(document, "$.designatedDeliveryDays"));

    }
}