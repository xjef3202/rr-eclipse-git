package com.sysco.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Opco {
    private String timeZone;
    private String cutoffTime;
    private String description;
    private String supportName;
    private String daylightSavingsCode;
    private String eMailAddress;
    private String receiptEmail;
    private String daysInAdvance;
    private String endecaEnabled;
    private String receiptSender;
    private String contactTelephone;
    private String emailTypeDefault;
    private String contactPersonsName;
    private String defaultCustomerEmail;
    private String listofEmailAddressforOPCO;
}
