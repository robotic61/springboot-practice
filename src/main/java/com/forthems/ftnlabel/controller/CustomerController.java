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

    // Spring doesn’t just create objects — it MANAGES and injects them,
    // so here Spring injects customerService object by spring creating the object AND spring managing them

    @PostMapping("/customers/save")
    // Handles both INSERT and UPDATE using repository.save()
    // NO id in JSON body → INSERT new row
    // HAVE id in JSON body and id exists and matches a row → UPDATE that row
    // JPArepository handles the logic in the background
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
        // handles both insert and update, send id = update, no id = insert
    }
    /*
    If input id does not exists for example:

    {
      "id": 1000,
      "name": "saveTestUPDATEString2",
      "email": "saveTesUPDATE100@gmail.com"
    }

    then ERROR!:
    {
      "timestamp": "2026-04-29T04:27:52.486Z",
      "status": 500,
      "error": "Internal Server Error",
      "path": "/customers/save"
    }

    id = null → INSERT ✔
    id exists → UPDATE ✔
    id not exist → ERROR ❌

     */

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


    @DeleteMapping("/customers/delete")
    // can be POST or DELETE method, since delete can also have a body.
    // but should use delete cuz its clear that we are deleting.
    public void deleteCustomer(@RequestBody Customer customer) {
        customerService.deleteCustomer(customer.getId());
        // 🔴dont write id in url path(it is not safe and can get attacked) so should change to get id from JSON field.
        // only sends ID in JSON field.
        // ❗ Prevent ID guessing (IDOR attacks)
        // https encrypts(change to some hard to read format) JSON body
        // HTTPS encrypts path + query + JSON body
        // BUT URLs can still be exposed via logs/history
        // so https still needs to hide sensitive url path details(to protects the connection)
        // 🔥 REAL security solution
        // ✔ Authentication (who are you)
        // ✔ Authorization (what can you access)
        /*
        save() → read form (getter)
                find() → fill form (setter)
                delete() → just delete by ID (no objects)
                DELETE FROM customer WHERE id = 3;
         */

        /*
        Example:

        {
         "id": 5,
        }

        here other fields = null

        {
          "id": 5,
          "email": "Yohan@gmail.com",
          "name": "Yohan"
        }

        we only do cutomer.getid() on the object so other fields doesn't need to be input
        , it just stays there inside the object.
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

//    @PutMapping("/customers/{id}")
//    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
//        return customerService.updateCustomer(id, customer);
//        // update at id from the path variable
//    }

    @GetMapping("/customers/searchnamecontaining")
    public List<Customer> searchNameContaining(@RequestParam String word) {
        return customerService.searchNameContaining(word);
    }
   //  for GET can use @RequestParam

//    // Example:
//    // GET http://localhost:8080/customers/searchnamecontaining?word=mm
//    // ?word=mm → "mm"
//    // word = "mm"

//    @GetMapping("/customers/namecontains") // POST or GET?
//    public List<Customer> searchNameContaining(@RequestBody Customer customer) {
//        return customerService.searchNameContaining(customer.getName());
//    } // Or change to POST, but the intention is not satisfied
//    // or wants to avoid url encoding characters, when we need to use special characters, like space(%20) or !

    @GetMapping("/customers/namestarts")
    public List<Customer> searchNameStartingWith(@RequestBody Customer customer) {
        return customerService.searchNameStartingWith(customer.getName());
    }

    @DeleteMapping("/customers/deletebyname")
    public void deleteByName(@RequestParam String word) {
        customerService.deleteByName(word);
    }

    /*
    | Method           | Type           | Transaction               |
    | ---------------- | -------------- | ------------------------- |
    | `deleteById()`   | built-in       | ✔ already handled         |
    | `deleteByName()` | derived/custom | ❗ may need @Transactional |

     */
}



/*
Now:
1. create custom sql example: (select name from customer where name like "%mm%")
% = anything, mm = substring
2. create custom sql inside the repository as a method
 */
