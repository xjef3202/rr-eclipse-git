package com.sysco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

     private String completeCategoryId ;
     private int mainCategoryId;
     private String label;
}
