package com.sysco.enums;

public enum Response {

    NODATA("noData"),
    HTTPSTATUSCODE("statusCode"),
    MESSAGE("message");

    String value;

    Response (String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
