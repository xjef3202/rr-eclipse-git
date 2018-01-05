package com.sysco.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PackSize {
    private String pack;
    private String size;
    private String uom;
}