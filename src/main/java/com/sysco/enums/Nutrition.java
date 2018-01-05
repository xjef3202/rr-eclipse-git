package com.sysco.enums;

public enum Nutrition {
    INGREDIENTS("ingredients"),
    ALLERGENS("allergens"),
    SERVINGSIZE("servingSize"),
    SERVINGSIZEWEIGHT("servingSizeWeight"),
    CALORIES("calories"),
    CALORIESFROMFAT("caloriesFromFat"),
    CALORIESFROMSATURATEDFAT("caloriesFromSaturatedFat"),
    TOTALFAT("totalFat"),
    SATURATEDFAT("saturatedFat"),
    TRANSFAT("transFat"),
    POLYUNSATURATEDFAT ("polyUnsaturatedFat"),
    MONOUNSATURATEDFAT("monoUnSaturatedFat"),
    CHOLESTEROL("cholesterol"),
    SODIUM("sodium"),
    POTASSIUM("potassium"),
    TOTALCARBOHYDRATE("totalCarbohydrate"),
    DIETARYFIBER("dietaryFiber"),
    SUGARS("sugars"),
    PROTEIN("protein"),
    VITAMINA("vitaminA"),
    VITAMINC("vitaminC"),
    CALCIUM("calcium"),
    IRON("iron"),
    VITAMIND("vitaminD"),
    THIAMIN("thiamin"),
    RIBOFLAVIN("riboflavin"),
    NIACIN("niacin"),
    VITAMINB6("vitaminB6"),
    FOLATE("folate"),
    VITAMINB12("vitaminB12"),
    PANTOTHENICACID("pantothenicacid"),
    PHOSPHORUS("phosphorus"),
    MAGNESIUM("magnesium"),
    ZINC("zinc"),
    SELENIUM("selenium"),
    COPPER("copper"),
    MANGANESE("manganese"),
    GLUTENFREE("glutenFree"),
    KOSHERCERTIFIED("kosherCertified"),
    TYPEOFKOSHER("typeOfKosher"),
    HALALCERTIFIED("halalCertified"),
    GRADE("grade"),
    PRODUCTBENEFITS("productBenefits"),
    VEGETARIAN("vegetarian"),
    TYPEOFVEGETARIAN("typeOfVegetarian"),
    VEGAN("vegan"),
    ORGANIC("organic"),
    TYPEOFORGANIC("typeOfOrganic"),
    TYPEOFGREEN("typeOfGreen"),
    GREEN("green"),
    CHILDNUTRITION("childNutrition"),
    PRODUCTFORMULATIONSTATEMENT("productFormulationStatement"),
    CNIDENTIFICATION("cnIdentification"),
    NOTES("notes"),
    INCLUDEDSUGARS("includedSugars"),
    INCLUDEDSUGARSUOM("includedSugarsUOM"),
    INCLUDEDSUGARSDAILYVALUE("includedSugarsDailyValue");

    String value;

    Nutrition (String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
