package com.sysco.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StockStatus {
    D("Demand status"),L("Low on Stock"),O("Out of Stock"),R("Remote stock"),N("Non-stock"),S("Warehouse");

    String value;
    StockStatus(String value){
    this.value = value;
    }


   public static StockStatus getStockStatus(String value){
        if(value==null)
            return null;
        return StockStatus.valueOf(value);
    }
    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String getValue(){
        return this.value;
    }
}
