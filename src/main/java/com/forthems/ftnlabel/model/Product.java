package com.forthems.ftnlabel.model;

/*
Creation order:
Data → Logic → API
Model → Service → Controller
 */

public class Product {
    private String name;
    private float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}

/*
1. Create product table
2. id, name, price.
3. insert, update, delete, select
4. using JPArepository, eg. findby, delete, insert
5. SELECT custom sql command, no more findby
 */
