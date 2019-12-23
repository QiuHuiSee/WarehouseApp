package com.android.warehouseapplication;

import android.app.Application;

public class GlobalClass extends Application {
    private String users;
    private String product;
    private String stock;

    public void setProduct(String product) {
        this.product = product;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getUsers() {
        return users;
    }

    public String getStock() {
        return stock;
    }

    public String getProduct() {
        return product;
    }
}
