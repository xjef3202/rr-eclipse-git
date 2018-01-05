package com.sysco.enums;

public enum SyscoCategory {

    HLTHCAR_OR_HOSPLTY (1,  "Hlthcar/Hosplty"),
    DAIRY_PRODUCT      (2,  "Dairy Product"),
    MEATS              (3,  "Meats"),
    SEAFOOD            (4,  "Seafood"),
    POULTRY            (5,  "Poultry"),
    FROZEN             (6,  "Frozen"),
    CANNED_AND_DRY     (7,  "Canned"),
    PAPER_AND_DISP     (8,  "Paper & Disp"),
    CHEMICAL_OR_JANTRL (9,  "Chemical"),
    SUPP_AND_EQUIP     (10, "Supp & Equip"),
    PRODUCE            (11, "Produce"),
    DISPENSER_BEVRG    (12, "Dispenser Bevrg");

    private final int code;
    private final String name;

    SyscoCategory(final int code, final String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
