package com.sysco.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Products {
    Map<String,Product> products;
}
