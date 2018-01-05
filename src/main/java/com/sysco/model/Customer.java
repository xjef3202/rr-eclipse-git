package com.sysco.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String telephoneNumber;
    private String cutoffTime;
    private int daysInAdvance;
    private String useOpcoCutoffTime;
    private String designatedDeliveryDays;
    private String state;
    private String contactFax;
    private String accountType;
    private String countryCode;
    private String customerName;
    private String customerContact;
    private String orderTemplateOne;
    private String orderTemplateTwo;
    private String orderTemplateFive;
    private String orderTemplateFour;
    private String customerActiveFlag;
    private String orderTemplateThree;
    private String city;
    private String zipCode;
    private String showRestrictedItems;
    private String orderReminderEnabled;
    private String endecaEnabled;
    private String productGuideAccess;
    private String territoryId;
    private String territoryName;
    private String priceLevel;
    private String priceRuleName;
    private String overrideChartName;
    private String overrideChartOrigin;
    private String substitutionRestrictionChartName;
    private String substitutionRestrictionChartOrigin;
}
