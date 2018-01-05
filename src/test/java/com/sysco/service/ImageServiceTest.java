package com.sysco.service;

import com.sysco.TestUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static com.sysco.ReportServiceTestData.IMG_URL;


public class ImageServiceTest {

    private ImageService imageService;

    private String supc="0109425";

    @Test
    public void getImageInfoTest() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        imageService = new ImageService(TestUtils.IMAGE_SVC_URL, restTemplate);
        String response = imageService.getImageInfo(supc);
        JSONObject jsonObject = new JSONObject(response);
        String imagePath = jsonObject.getJSONObject("Product").getJSONObject("images").getJSONObject("web").getJSONArray("urls").getString(0);
        Assert.assertEquals(IMG_URL,imagePath);
    }

    @Test
    public void testHttpTimeout() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(1);
        requestFactory.setReadTimeout(1);
        restTemplate.setRequestFactory(requestFactory);
        imageService = new ImageService(TestUtils.IMAGE_SVC_URL, restTemplate);
        String response = imageService.getImageInfo(supc);
        assert response == null;
    }
}
