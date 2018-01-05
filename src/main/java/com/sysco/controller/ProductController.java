package com.sysco.controller;

import com.sysco.model.ItemHistory;
import com.sysco.service.ProductService;
import com.sysco.service.ReportService;
import com.sysco.utils.ResponseUtil;
import com.sysco.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value="/{supc}", method = RequestMethod.GET)
    public ResponseEntity<String> getProduct(@PathVariable(value="supc") String supc ,
                                             @RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) String auth) {

        long start = System.currentTimeMillis();

        log.info("getProduct() : Supc = {}", supc);
        log.info("getProduct() : Authorization Header = {}", auth);
        ValidationUtil.validateAuthHeader(auth);
        String [] opcoAndCustomerId = auth.split("\\|");
        String serializedProduct = productService.getSerializedProduct(supc, opcoAndCustomerId[0], opcoAndCustomerId[1]);

        ResponseEntity<String> responseEntity = ResponseUtil.getResponseEntity(serializedProduct, HttpStatus.OK);

        long elapsedTime = System.currentTimeMillis() - start;

        log.info("operation=getProduct() duration={} : supc = {}; response = {}", elapsedTime, supc, serializedProduct);

        return responseEntity;
    }


    @RequestMapping(value = "/history/{opco}/{customerId}/{supc}", method = RequestMethod.GET)
    public ItemHistory getItemHistory(@PathVariable(value = "opco") String opco, @PathVariable(value = "customerId") String customerId,
                                      @PathVariable(value = "supc") String supc) {

        long start = System.currentTimeMillis();

        ItemHistory responseEntity = productService.getItemHistory(opco, customerId, supc);

        long elapsedTime = System.currentTimeMillis() - start;

        log.info("operation=getItemHistory() duration={} : opco = {}, shipTo = {}, supc = {}", elapsedTime, opco, customerId, supc);

        return responseEntity;
    }

    @RequestMapping(value = "/print", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity getJasperReport(@RequestParam(value = "supc") String supc,
                                          @RequestParam(value="customerId") String customerId,
                                          @RequestParam(value="opco") String opco) {

        long start = System.currentTimeMillis();

        byte [] content = reportService.generateReport(supc,opco,customerId);
        ResponseEntity<byte []> responseEntity = ResponseUtil.getResponseEntity(content);

        long elapsedTime = System.currentTimeMillis() - start;

        log.info("operation=getReport() duration={} : Supc = {}, AccountName = {}, AccountNumber = {}, opco = {}", elapsedTime, supc, customerId, opco);

        return responseEntity;
    }
}