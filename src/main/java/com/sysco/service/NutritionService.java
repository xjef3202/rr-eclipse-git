package com.sysco.service;

import com.sysco.model.NutritionResponse;
import com.sysco.utils.SerializationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class NutritionService {
    private String productServiceUrl;

    private RestTemplate restTemplate;

    public NutritionService(String productServiceUrl, RestTemplate restTemplate) {
        this.productServiceUrl = productServiceUrl;
        this.restTemplate = restTemplate;
    }

    public String getNutrition(String supc) {
        ResponseEntity<NutritionResponse> nutritionResponseResponseEntity = restTemplate.getForEntity(String.join("/",productServiceUrl,"nutrition",supc),NutritionResponse.class);
        return SerializationUtil.serialize(nutritionResponseResponseEntity.getBody());
    }

    public NutritionResponse getNutritionObject(String supc) {
        ResponseEntity<NutritionResponse> nutritionResponseResponseEntity = restTemplate.getForEntity(String.join("/",productServiceUrl,"nutrition",supc),NutritionResponse.class);
        return nutritionResponseResponseEntity.getBody();
    }
}
