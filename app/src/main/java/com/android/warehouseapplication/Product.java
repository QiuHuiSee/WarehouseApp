package com.android.warehouseapplication;

public class Product {
    private String ID;
    private String Description;
    private String Cost;
    private String Manufacturer;

    public Product(String ID, String Description, String Cost, String Manufacturer) {
        this.ID = ID;
        this.Description = Description;
        this.Cost = Cost;
        this.Manufacturer = Manufacturer;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getCost() {
        return Cost;
    }

    public String getDescription() {
        return Description;
    }

    public String getID() {
        return ID;
    }

    public String getManufacturer() {
        return Manufacturer;
    }
}
