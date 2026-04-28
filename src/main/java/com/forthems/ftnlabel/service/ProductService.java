package com.forthems.ftnlabel.service;

import com.forthems.ftnlabel.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public Product testProduct() {
        Product product = new Product();
        product.setName("Macbook");
        product.setPrice(100000);
        return product;
    }

    public Product createProduct(Product product) {
        System.out.println("Product name received: " + product.getName());
        System.out.println("Product price: " + product.getPrice());
        return product;
    }

    public Product conditionalProduct(Product product) {
        product.setName(product.getName().toUpperCase()); // set name as uppercase
        if (product.getPrice() == 0) {
            product.setPrice(10); // if no price from client(POST), then set price = 10
        }
        return product;
    }
}
