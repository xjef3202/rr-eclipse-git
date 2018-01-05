package com.sysco.service;

import com.sysco.model.ItemHistory;
import com.sysco.model.Product;
import com.sysco.utils.SerializationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductService {

    private String productServiceUrl;

    private RestTemplate restTemplate;

    public ProductService(String productServiceUrl, RestTemplate restTemplate) {
        this.productServiceUrl = productServiceUrl;
        this.restTemplate = restTemplate;
    }

    public Map<String, Product> getProducts(String opco, String customerId, List<String> supcList) {
        URI uri = UriComponentsBuilder.fromHttpUrl(productServiceUrl)
                                        .queryParam("opco", opco)
                                        .queryParam("customerId", customerId)
                                        .queryParam("supcList", supcList.toArray())
                                        .build().encode().toUri();
        log.info("getProducts(opco: {}, customerId: {}, supcList: {}) : Product Service Call Request", opco, customerId, supcList);
        return restTemplate.exchange(new RequestEntity<>(HttpMethod.GET, uri),
                                        new ParameterizedTypeReference<Map<String, Product>>(){}).getBody();
    }

    Product getProduct(String supc, String opco, String customerId) {
        log.info("getProduct(supc: {}, opco: {}, customerId: {}) : Product Service Call Request", supc, opco, customerId);
        ResponseEntity<Product> productResponseEntity = restTemplate.getForEntity(String.join("/", productServiceUrl, supc, opco, customerId), Product.class);
        log.info("getProduct() : Product Service Call Response  "+productResponseEntity.getHeaders() +" ProductSvcUrl= "+productServiceUrl);
        return productResponseEntity.getBody();
    }

    public String getSerializedProduct(String supc, String opco, String customerId){
        Product product = getProduct(supc, opco, customerId);
        return SerializationUtil.serialize(product);
    }

    public ItemHistory getItemHistory(String opco, String customerId, String supc) {
        return restTemplate.getForEntity(String.join("/", productServiceUrl, "history", opco, customerId, supc), ItemHistory.class).getBody();
    }
}
