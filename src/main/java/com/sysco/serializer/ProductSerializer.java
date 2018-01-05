package com.sysco.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sysco.model.Product;

import java.io.IOException;

public class ProductSerializer extends StdSerializer<Product>{

    public ProductSerializer() {
        this(null);
    }
    public ProductSerializer(Class<Product> t) {
        super(t);
    }

    @Override
    public void serialize(Product product, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", emptyIfNull(product.getName()));
        gen.writeStringField("productCode", emptyIfNull(product.getMaterialId()));
        gen.writeStringField("supplier", emptyIfNull(product.getSupplier()));
        gen.writeStringField("brand", emptyIfNull(product.getBrand()));
        gen.writeStringField("type", emptyIfNull(product.getType()));
        gen.writeStringField("description", emptyIfNull(product.getDescription()));
        gen.writeStringField("nickName", emptyIfNull(product.getNickName()));
        gen.writeStringField("averageWeight", product.getAverageWeightPerCase());

        addPackSize(product, gen);

        gen.writeStringField("deleted", convertValue(product.isDeleted()));

        addUserImages(product, gen);

        addSplitCode(product, gen);

        gen.writeStringField("isCatchWeight", convertValue(product.isCatchWeight()));
        gen.writeStringField("isAvailable", convertValue(product.isAvailable()));
        gen.writeStringField("replacementMaterialId", emptyIfNull(product.getReplacementMaterialId()));
        gen.writeStringField("hasReplacement", convertValue(product.isHasReplacement()));
        gen.writeStringField("isOrderable", convertValue(product.isOrderable()));
        gen.writeStringField("lastOrderedDate",product.getLastOrderedDate());

        addCategory(product, gen);

//        addMessages(product, gen);

        addFlags(product, gen);

        addSplit(product, gen);

        gen.writeStringField("stockIndicator", emptyIfNull(product.getStockIndicator()));

        addStockStatus(product, gen);

        gen.writeStringField("coreIndicator", emptyIfNull(product.getCoreIndicator()));

        addClassification(product, gen);

        addIsSoldAs(product,gen);

        gen.writeStringField("externalUPC",emptyIfNull(product.getExternalUPC()));
        gen.writeStringField("manufacturerCode",emptyIfNull(product.getManufacturerCode()));
        gen.writeStringField("thirdLineDescription",emptyIfNull(product.getLineDescription()));
        gen.writeStringField("storageLocation",emptyIfNull(product.getStorageFlag()));
        gen.writeStringField("portionSize",emptyIfNull(product.getPortionSize()));
        gen.writeEndObject();
    }

    private void addIsSoldAs(Product product, JsonGenerator gen) throws IOException {
        if(product.getIsSoldAs()!=null) {
            gen.writeObjectFieldStart("isSoldAs");
            gen.writeStringField("isCase",convertValue(product.getIsSoldAs().isCase()));
            gen.writeStringField("split",convertValue(product.getIsSoldAs().isSplit()));
            gen.writeEndObject();
        }else {
            gen.writeObjectFieldStart("isSoldAs");
            gen.writeEndObject();
        }
    }

    private void addClassification(Product product, JsonGenerator gen) throws IOException {
        if (product.getClassification() != null){
            gen.writeStringField("classification", product.getClassification().getValue());
        }else {
            gen.writeStringField("classification", "");
        }
    }

    private void addStockStatus(Product product, JsonGenerator gen) throws IOException {
        if (product.getStockStatus() != null) {
            gen.writeStringField("stockStatus", product.getStockStatus().getValue());
        }else {
            gen.writeStringField("stockStatus", "");
        }
    }

    private void addSplit(Product product, JsonGenerator gen) throws IOException {
        if(product.getSplit()!=null) {
            gen.writeObjectFieldStart("split");
            gen.writeNumberField("min",product.getSplit().getMin());
            gen.writeNumberField("max",product.getSplit().getMax());
            gen.writeEndObject();
        }else {
            gen.writeObjectFieldStart("split");
            gen.writeEndObject();
        }
    }

    private void addFlags(Product product, JsonGenerator gen) throws IOException {
        gen.writeArrayFieldStart("flags");
        if (product.getFlags() != null) {
            for (String flag : product.getFlags()) {
                gen.writeString(flag);
            }
        }
        gen.writeEndArray();
    }

//    private void addMessages(Product product, JsonGenerator gen) throws IOException {
//        gen.writeArrayFieldStart("messages");
//        if (product.getMessages() != null) {
//            for (String msg : product.getMessages()) {
//                gen.writeString(msg);
//            }
//        }
//        gen.writeEndArray();
//    }

    private void addCategory(Product product, JsonGenerator gen) throws IOException {
        if(product.getCategory()!=null) {
            gen.writeObjectFieldStart("category");
            gen.writeStringField("completeCategoryId",product.getCategory().getCompleteCategoryId());
            gen.writeNumberField("mainCategoryId",product.getCategory().getMainCategoryId());
            gen.writeStringField("label",product.getCategory().getLabel());
            gen.writeEndObject();
        }else {
            gen.writeObjectFieldStart("category");
            gen.writeEndObject();
        }
    }

    private void addSplitCode(Product product, JsonGenerator gen) throws IOException {
        if (product.getIsSoldAs() != null) {
            gen.writeStringField("splitCode", convertValue(product.getIsSoldAs().isSplit()));
        }else {
            gen.writeStringField("splitCode","");
        }
    }

    private void addUserImages(Product product, JsonGenerator gen) throws IOException {
        gen.writeArrayFieldStart("userImages");
        if (product.getImages() != null) {
            for (String img : product.getImages()) {
                gen.writeString(img);
            }
        }
        gen.writeEndArray();
    }

    private void addPackSize(Product product, JsonGenerator gen) throws IOException {
        if (product.getPackSize() != null) {
            gen.writeStringField("size", emptyIfNull(product.getPackSize().getSize()));
            gen.writeStringField("pack", emptyIfNull(product.getPackSize().getPack()));
        }else {
            gen.writeStringField("size", "");
            gen.writeStringField("pack", "");
        }
    }


    private String convertValue(boolean value){
        return value?"Y":"N";
    }

    private String emptyIfNull(String input){
        return input==null?"":input;
    }
}
