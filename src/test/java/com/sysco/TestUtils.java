package com.sysco;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class TestUtils {

    private static Properties props;

    public static String PRODUCT_SVC_URL;

    public static String NUTRITION_SVC_URL;

    public static String IMAGE_SVC_URL;

    public static String ITEM_SVC_URL;

    public static String USER_SVC_URL;

    public static String ORDER_SVC_URL;

    public static String ADMIN_SVC_URL;

    public static String CART_SVC_URL;

    static {
        props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("./src/test/resources/application.properties");
        } catch (FileNotFoundException e) {

        } finally {
            try {
                props.load(in);
                PRODUCT_SVC_URL = props.getProperty("product.svc.url");
                NUTRITION_SVC_URL = props.getProperty("nutrition.svc.url");
                IMAGE_SVC_URL = props.getProperty("image.svc.url");
                ITEM_SVC_URL = props.getProperty("item.svc.url");
                USER_SVC_URL = props.getProperty("user.svc.url");
                ORDER_SVC_URL = props.getProperty("order.svc.url");
                ADMIN_SVC_URL = props.getProperty("admin.svc.url");
                CART_SVC_URL = props.getProperty("cart.svc.url");

                in.close();
            } catch (IOException e) {

            }
        }
    }

    public static String getProp(String key) {
        return props.getProperty(key);
    }

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClients.createDefault();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        return restTemplate;
    }

    public static <T extends Object>T readJsonToObject(Class<T> classType, String fileName) {
        T object = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            object = mapper.readValue(new File("./schemas/" + fileName), classType);
        } catch (IOException e) {
            throw new RuntimeException("Not able to read json " + fileName, e);
        }

        return object;
    }


    public static <T extends Object>T readJsonToObject(TypeReference typeReference, String fileName) {
        T object = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            object = mapper.readValue(new File("./schemas/" + fileName), typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    public static String readJson(TypeReference typeReference, String filName) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(TestUtils.readJsonToObject(typeReference, filName));
    }

    public static String readFileToString(String fileName)  {
        String fileString = null;
        try {
            fileString = new String(Files.readAllBytes(Paths.get("./schemas/" + fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileString;
    }

}
