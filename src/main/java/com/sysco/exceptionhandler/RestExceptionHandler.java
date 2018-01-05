package com.sysco.exceptionhandler;


import com.sysco.exception.ValidationException;
import com.sysco.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {
    
    @ExceptionHandler(value= HttpClientErrorException.class)
    public ResponseEntity clientException(HttpClientErrorException e) throws JSONException {
        log.error("clientException() : Client Exception ", e);
        log.error("clientException() : Service Call Response Headers = {}", e.getResponseHeaders());
        log.error("clientException() : Service Call Response Body = {}", e.getResponseBodyAsString());
        String responseBody = getResponseBody(e.getStatusText());
        return ResponseUtil.getResponseEntity(responseBody,e.getStatusCode());
    }

    @ExceptionHandler(value = HttpServerErrorException.class)
    public ResponseEntity serverException(HttpServerErrorException e) throws JSONException{
        log.error("serverException() : Service Exception", e);
        log.error("serverException() : Service Call Response Headers = {}", e.getResponseHeaders());
        log.error("serverException() : Service Call Response Body = {}", e.getResponseBodyAsString());
        String responseBody = getResponseBody("Internal Service Error");
        return ResponseUtil.getResponseEntity(responseBody,e.getStatusCode());
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity validationException(ValidationException e) throws JSONException{
        log.error("validationException() : ", e);
        String responseBody = getResponseBody(e.getMessage());
        return ResponseUtil.getResponseEntity(responseBody,e.getStatusCode());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity catchAll(Exception e) throws JSONException {
        log.error("catchAll() : Internal Service Error", e);
        String responseBody = getResponseBody("Internal Service Error");
        return ResponseUtil.getResponseEntity(responseBody,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getResponseBody(String message) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",message);
        return jsonObject.toString();
    }

}
