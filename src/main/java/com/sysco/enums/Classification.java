package com.sysco.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Classification {
    A("National Core"), K("Core-Closed Code"),M("Core-No Longer Used"),Z("Core-Special Order"),F("Core-Local Exceptions"),C("Corporate Proprietary"),
    L("Non-core Close Coded"),E("Non-core Local Exceptions"),S("Non-core Special Order"),N("Non-core No longer used"),X("No Longer Manufactured"),T("Transactional");

    String value;
    Classification(String value){
        this.value = value;
    }

    public static Classification getClassification(String value){
        if(value==null){
            return null;
        }
        return Classification.valueOf(value);
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
