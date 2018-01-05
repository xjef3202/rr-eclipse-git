package com.sysco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sysco.enums.Classification;
import com.sysco.enums.StockStatus;
import com.sysco.serializer.ProductSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonSerialize(using = ProductSerializer.class)
public class Product {
    private String name;
    private String materialId;
    private String supplier;
    private String brand;
    private String type;
    private String description;
    private String nickName;
    private String averageWeightPerCase;
    private PackSize packSize;
    private boolean deleted;
    private String [] userImages;
    private IsSoldAs isSoldAs;
    @JsonProperty("isCatchWeight")
    private boolean isCatchWeight;
    private String storageFlag;
    @JsonProperty("isAvailable")
    private boolean isAvailable;
    private String replacementMaterialId;
    private boolean hasReplacement;
    private Category  category;
    @JsonIgnore
    private Message [] messages ;
    @JsonProperty("isOrderable")
    private boolean isOrderable;
    private boolean canBuy;
    private String lastOrderedDate;
    private String [] images;
    private String [] flags;
    private Split split;
    private String stockIndicator;
    private StockStatus stockStatus;
    private String coreIndicator;
    private Classification classification;
    private String externalUPC;
    private String manufacturerCode;
    private String lineDescription;
    private String portionSize;

}


