package com.forthems.ftnlabel.controller;


import com.forthems.ftnlabel.model.Product;
import com.forthems.ftnlabel.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/test")
    public Product productTest() {
        return productService.testProduct();
    }

    @PostMapping("/product")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PostMapping("/product/conditional")
    public Product condtionalProduct(@RequestBody Product product) {
        return productService.conditionalProduct(product);
    }

}
