package com.forthems.ftnlabel.controller;


import com.forthems.ftnlabel.model.Customer;
import com.forthems.ftnlabel.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    /*
    Returned example:
    [
  {
    "email": null,
    "id": 1,
    "name": "Ipad"
  },
  {
    "email": null,
    "id": 2,
    "name": "Ipad"
  },
  {
    "email": "David@gmail.com",
    "id": 3,
    "name": "David"
  }
]
     */





}
