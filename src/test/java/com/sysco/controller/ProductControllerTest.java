package com.sysco.controller;

import com.sysco.product.ProductTestData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static com.sysco.product.ProductTestData.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    private static final String PRODUCT_ENDPOINT = "/orch/products";
    private static final String AUTH_HEADER = "010|042374";

    @Test
    public void shouldRespondWithOkStatusCode(){
        int statusCode = given().port(port).header(HttpHeaders.AUTHORIZATION,AUTH_HEADER)
                                .get(PRODUCT_ENDPOINT+"/"+ SUPC).getStatusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void shouldRespondForRealSUPC(){
        ResponseBody body = given().port(port).header(HttpHeaders.AUTHORIZATION,AUTH_HEADER)
                .get(PRODUCT_ENDPOINT + "/" + SUPC).getBody();
        JsonPath productResponse = body.jsonPath();
        assertThat(productResponse.getString("productCode")).isEqualTo(SUPC);
        assertThat(productResponse.getString("name")).isEqualTo(NAME);
        assertThat(productResponse.getString("thirdLineDescription")).isEqualTo(THIRD_LINE_DESCRIPTION);
    }

    @Test
    public void shouldRespondUnauthorized(){
        int statusCode = given().port(port).header(HttpHeaders.AUTHORIZATION,"")
                .get(PRODUCT_ENDPOINT + "/" + Integer.MIN_VALUE).getStatusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void shouldSerializeResponseWithNewFieldNames() throws Exception {
        ResponseBody body = given().port(port).header(HttpHeaders.AUTHORIZATION,AUTH_HEADER)
                .get(PRODUCT_ENDPOINT + "/" + SUPC).getBody();
        JsonPath productResponse = body.jsonPath();
        List<Object> userImagesArray = productResponse.getList("userImages");
        Map category = productResponse.get("category");

        assertThat(productResponse.getString("splitCode")).isEqualTo(SPLIT_CODE);
        assertThat(productResponse.getString("storageLocation")).isEqualTo(STORAGE_LOCATION);
        assertThat(userImagesArray.get(0)).isEqualTo(IMAGE_URL);
        assertThat(category.get("mainCategoryId")).isEqualTo(ProductTestData.MAIN_CATEGORY_ID);
        assertThat(category.get("label")).isEqualTo(ProductTestData.LABEL);

    }
}