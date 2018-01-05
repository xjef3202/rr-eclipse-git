package com.sysco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    private String supc;
    private String soldAs;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String priceUOM;
}
