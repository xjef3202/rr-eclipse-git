package com.sysco.service;

import com.sysco.Application;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.sysco.NutritionTestData.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest({"server.port=0"})
public class NutritionControllerTest {

    @Value("${local.server.port}")
    int port;
    public static final String NUTRITION_ENDPOINT = "/orch/nutritions";
    private static final String SUPC = "0587840";
    public static final String AUTH_HEADER = "067|000018";

    @Test
    public void shouldRespondWithOkStatusCode(){
        int statusCode = given().port(port).header(HttpHeaders.AUTHORIZATION,AUTH_HEADER)
                .get(NUTRITION_ENDPOINT +"/"+ SUPC).getStatusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void shouldRespondWithSerializedNutritionInformation() throws Exception {
        ResponseBody body = given().port(port).header(HttpHeaders.AUTHORIZATION,AUTH_HEADER)
                .get(NUTRITION_ENDPOINT + "/" + SUPC).getBody();
        JsonPath nutritionResponse = body.jsonPath();

        assertEquals(INGREDIENT,nutritionResponse.getString("ingredients"));
        assertEquals(ALLERGENS,nutritionResponse.getString("allergens"));
        assertEquals(SERVING_SIZE,nutritionResponse.getString("servingSize"));
        assertEquals(SERVING_SIZE_WEIGHT,nutritionResponse.getString("servingSizeWeight"));
        assertEquals(CALORIES,nutritionResponse.getString("calories"));
        assertEquals(CALORIES_UOM,nutritionResponse.getString("caloriesUOM"));
        assertEquals(TOTAL_FAT,nutritionResponse.getString("totalFat"));
        assertEquals(TOTAL_FAT_UOM,nutritionResponse.getString("totalFatUOM"));
        assertEquals(TOTAL_FAT_DAILY_VALUE,nutritionResponse.getString("totalFatDailyValue"));
        assertEquals(CHOLESTEROL,nutritionResponse.getString("cholesterol"));
        assertEquals(CHOLESTEROL_UOM,nutritionResponse.getString("cholesterolUOM"));
        assertEquals(CHOLESTEROL_DAILY_VALUE,nutritionResponse.getString("cholesterolDailyValue"));
        assertEquals(VITAMIN_A,nutritionResponse.getString("vitaminA"));
        assertEquals(KOSHER_CERTIFIED,nutritionResponse.getString("kosherCertified"));
        assertEquals(NOTES,nutritionResponse.getString("notes"));

    }
}
