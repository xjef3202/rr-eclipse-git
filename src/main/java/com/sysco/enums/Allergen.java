package com.sysco.enums;

public enum Allergen {

    Egg("egg.png"),Eggs("egg.png"),Fish("fish.png"),Milk("milk.png"),Peanut("nuts.png"),ShellFish("shellfish.png"),Soybeans("soy.png"),Soy("soy.png"),TreeNut("treenut.png"),Wheat("wheat.png"),Gluten("gluten.png");

    String value;

    Allergen(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
