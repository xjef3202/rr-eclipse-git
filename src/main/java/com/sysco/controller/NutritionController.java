package com.sysco.controller;

import com.sysco.service.NutritionService;
import com.sysco.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nutritions")
@Slf4j
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @RequestMapping(value="/{supc}", method = RequestMethod.GET)
    public ResponseEntity<String> getNutrition(@PathVariable(value="supc") String supc) {

        long start = System.currentTimeMillis();

        String nutrition = nutritionService.getNutrition(supc);

        long elapsedTime = System.currentTimeMillis() - start;

        log.info("operation=getNutrition() duration={} : supc = {}; response = {}", elapsedTime, supc, nutrition);

        return ResponseUtil.getResponseEntity(nutrition, HttpStatus.OK);
    }

}
