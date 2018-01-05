package com.sysco.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sysco.TestUtils;
import com.sysco.model.Product;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sysco.product.ProductTestData.SUPC;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductServiceTest {

    private static final String OPCO = "010";
    private static final String CUSTOMERID = "042374";

    private ProductService productService;

    @Before
    public void setup() {
        RestTemplate restTemplate = new RestTemplate();

        productService = new ProductService(TestUtils.PRODUCT_SVC_URL, restTemplate);
    }

    @Test
    public void getProduct_shouldGetResponseForValidSUPCs() throws JSONException, IOException {
        Product expectedProduct = TestUtils.readJsonToObject(Product.class, "productResponse.json");
        String product = productService.getSerializedProduct(SUPC, OPCO, CUSTOMERID);
        JSONObject proudctJson = new JSONObject(product);
        assertThat(proudctJson.getString("productCode")).isEqualTo(expectedProduct.getMaterialId());
        assertThat(proudctJson.getString("name")).isEqualTo(expectedProduct.getName());
        assertThat(proudctJson.getString("description")).isEqualTo(expectedProduct.getDescription());
        assertThat(proudctJson.getString("averageWeight")).isEqualTo(String.valueOf(expectedProduct.getAverageWeightPerCase()));
        assertThat(proudctJson.getString("pack")).isEqualTo(expectedProduct.getPackSize().getPack());
        assertThat(proudctJson.getString("size")).isEqualTo(expectedProduct.getPackSize().getSize());
        assertThat(proudctJson.getJSONArray("userImages").getString(0)).isEqualTo(expectedProduct.getImages()[0]);
        assertThat(proudctJson.getString("stockIndicator")).isEqualTo(expectedProduct.getStockIndicator());
        assertThat(proudctJson.getString("stockStatus")).isEqualTo(expectedProduct.getStockStatus().toString());
        assertThat(proudctJson.getString("storageLocation")).isEqualTo(expectedProduct.getStorageFlag());
        assertThat(proudctJson.getString("stockIndicator")).isEqualTo(expectedProduct.getStockIndicator());
        assertThat(proudctJson.getString("thirdLineDescription")).isEqualTo(expectedProduct.getLineDescription());
        assertThat(proudctJson.getString("manufacturerCode")).isEqualTo(expectedProduct.getManufacturerCode());
    }

    @Test
    public void getProducts_shouldGetProductsForMultipleSUPCs() throws IOException, URISyntaxException {
        TypeReference<Map<String,Product>> typeReference =
                new TypeReference<Map<String,Product>>(){};
        Map<String, Product> productList = TestUtils.readJsonToObject(typeReference,"multipleProductsResponse.json");
        Map<String, Product> products = productService.getProducts(OPCO, CUSTOMERID, productList.keySet().stream().collect(Collectors.toList()));
        productList.keySet().forEach( supc ->
                assertThat(products.get(supc)).isEqualTo(productList.get(supc))
        );
    }
}