package com.sysco.utils;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ResponseUtil {

    public static ResponseEntity<String> getResponseEntity(String responseBody,HttpStatus httpStatus) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        return new ResponseEntity<>(responseBody, headers, httpStatus);
    }

    public static ResponseEntity<byte[]> getResponseEntity(byte [] responseBody) {
        String fileName = "Print_ProductCard.pdf";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\""+fileName);
        headers.add(HttpHeaders.CACHE_CONTROL,"maxage=3600");
        headers.add(HttpHeaders.EXPIRES,"0");
        headers.add(HttpHeaders.PRAGMA,"public");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        return new ResponseEntity<byte []>(responseBody, headers, HttpStatus.OK);
    }

}
