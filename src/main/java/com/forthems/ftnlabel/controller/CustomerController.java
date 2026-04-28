package com.forthems.ftnlabel.controller;


import com.forthems.ftnlabel.model.Customer;
import com.forthems.ftnlabel.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    /*
    Can API (URL) be the same?

    YES — same URL, different method

    Example:
    POST   /customers       → create
    GET    /customers       → get all
    GET    /customers/{id}  → get one
    PUT    /customers/{id}  → update
    DELETE /customers/{id}  → delete

    Spring uses:
    HTTP METHOD + URL

    URL = location
    Method = action

    Room = /customers/1
    Action = GET / PUT / DELETE
     */

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

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    /*
     When someone sends GET request to /customers/{id}, run this method.
     {id} = a variable (placeholder) in the URL
     “This part of the URL can change”
     This is a pattern, not a fixed URL.

     /customers/1  → id = 1
     /customers/10 → id = 10

     @PathVariable Long id)
     Take the id value from the URL and store it in this variable.
     BUT the name has to match
     */


    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        /*
        save() → read form (getter)
                find() → fill form (setter)
                delete() → just delete by ID (no objects)
                DELETE FROM customer WHERE id = 3;
         */
    }
    /*
    DELETE /customers/1
    → Controller receives id = 1
    → Service deletes customer with id = 1
    → Database row removed

    When someone sends DELETE request to /customers/{id}, run this method.
     */

    /*
    DELETE = intention / request type

    “I want to delete something”

    👉 But it does NOT guarantee anything is actually deleted

    @DeleteMapping("/customers/{id}")
    “If a DELETE request comes here → call this method”

    👉 Controller just receives and forwards to service
    ✔ 3. Service (THIS is where real logic happens)
    customerService.deleteCustomer(id);
    👉 Service decides:
    ✔ Delete if exists
    ✔ Do nothing if not found
    ✔ Throw error
    ✔ Log something

    HTTP method = request intention
    Service = actual behavior / decision

    HTTP method ≠ actual action
    Service logic = actual action

     */

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }
}
