package com.example.user.xenoblade3;

public class Element {

    private String type;
    private int typeId;

    public Element() {

        typeId = 0;
        type = "";
    }

    public Element(int id, String t) {

        typeId = id;
        type = t;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeElement(int id) {

        String element = "";

        switch (id) {

            case 1:
                element = "Fire";
                break;
            case 2:
                element = "Water";
                break;
            case 3:
                element = "Ice";
                break;
            case 4:
                element = "Earth";
                break;
            case 5:
                element = "Thunder";
                break;
            case 6:
                element = "Wind";
                break;
            case 7:
                element = "Light";
                break;
            case 8:
                element = "Dark";
                break;
        }

        return element;
    }

    public int getElementId(String elements) {

        int value = 1;

        switch (elements) {

            case "Fire":
                value = 1;
                break;
            case "Water":
                value = 2;
                break;
            case "Ice":
                value = 3;
                break;
            case "Earth":
                value = 4;
                break;
            case "Thunder":
                value = 5;
                break;
            case "Wind":
                value = 6;
                break;
            case "Light":
                value = 7;
                break;
            case "Dark":
                value = 8;
                break;
        }

        return value;

    }

    public int getElementImage(String elements) {

        int imageId = 0;

        switch(elements){

            case "Fire":
                imageId = R.drawable.element_fire;
                break;
            case "Water":
                imageId = R.drawable.element_water;
                break;
            case "Ice":
                imageId = R.drawable.element_ice;
                break;
            case "Earth":
                imageId = R.drawable.element_earth;
                break;
            case "Thunder":
                imageId = R.drawable.element_thunder;
                break;
            case "Wind":
                imageId = R.drawable.element_wind;
                break;
            case "Light":
                imageId = R.drawable.element_light;
                break;
            case "Dark":
                imageId = R.drawable.element_dark;
                break;

        }
        return imageId;
    }

}
