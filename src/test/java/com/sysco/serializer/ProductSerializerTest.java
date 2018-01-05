package com.sysco.serializer;



import com.sysco.enums.Classification;
import com.sysco.model.Category;
import com.sysco.model.IsSoldAs;
import com.sysco.model.Product;
import com.sysco.model.Split;
import com.sysco.utils.SerializationUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductSerializerTest {

    @Test
    public void serializeProductTest() throws JSONException {

        Product product = getProduct();

        String serializedProduct = SerializationUtil.serialize(product);

        JSONObject jsonObject = new JSONObject(serializedProduct);

        assertThat(jsonObject.getString("classification")).isEqualTo("National Core");
        assertThat(jsonObject.getDouble("averageWeight")).isEqualTo(4.4);
        assertThat(jsonObject.getString("stockIndicator")).isEqualTo("S");
        assertThat(jsonObject.getString("isAvailable")).isEqualTo("Y");
        assertThat(jsonObject.getString("size")).isEqualTo("");
        assertThat(jsonObject.getString("pack")).isEqualTo("");
        assertThat(jsonObject.getJSONArray("flags").length()).isEqualTo(0);
        assertThat(jsonObject.getJSONObject("split").getInt("min")).isEqualTo(4);
        assertThat(jsonObject.getJSONObject("split").getInt("max")).isEqualTo(4);
        assertThat(jsonObject.getJSONObject("category").getString("completeCategoryId")).isEqualTo("03004008003");
        assertThat(jsonObject.getJSONObject("category").getLong("mainCategoryId")).isEqualTo(3);
        assertThat(jsonObject.getJSONObject("category").getString("label")).isEqualTo("PRECOOKED SLI");
        assertThat(jsonObject.getJSONObject("isSoldAs").getString("isCase")).isEqualTo("Y");
        assertThat(jsonObject.getJSONObject("isSoldAs").getString("split")).isEqualTo("N");
    }

    private Product getProduct() {
        Product product = new Product();
        product.setClassification(Classification.A);
        product.setStockIndicator("S");
        product.setAvailable(true);
        product.setAverageWeightPerCase("4.4");
        product.setBrand("Sysoc");
        product.setFlags(null);
        product.setPackSize(null);
        product.setSplit(new Split(4,4));
        product.setCategory(new Category("03004008003",3,"PRECOOKED SLI"));
        product.setIsSoldAs(new IsSoldAs(true,false));
        return product;
    }
}
