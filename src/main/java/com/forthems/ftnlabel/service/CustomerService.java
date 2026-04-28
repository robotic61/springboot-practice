package com.forthems.ftnlabel.service;

import com.forthems.ftnlabel.model.Customer;
import com.forthems.ftnlabel.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    // same like controller class, we need
    // to use Repository, so we do constructor injection.
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
        // save RETURNS:
        //🔴 After saved object:
        /*
        saved:
        id = 1
        name = Temmy
        email = temmy@example.com
         */

    }
        // That Customer object comes from the controller.
        /*
        Example JSON:
        {
        "name": "Temmy",
        "email": "temmy@example.com"
        }

        Spring converts it into(using setter):

        Customer customer = new Customer();
        customer.setName("Temmy");
        customer.setEmail("temmy@example.com");
        Then controller passes it to service.

        return customerRepository.save(customer);
        Save this customer into the database.
        👉 It uses getters to read values
        Behind the scenes, Spring/JPA runs SQL similar to:
        INSERT INTO customer (name, email) VALUES ('Temmy', 'temmy@example.com');
        Then MySQL creates the id.
        id = 1
        Then save() returns the saved customer.
        So response becomes:
        {
          "id": 1,
          "name": "Temmy",
          "email": "temmy@example.com"
        }


        POST JSON
        → Controller receives Customer
        → Controller calls customerService.saveCustomer(customer)
        → Service calls customerRepository.save(customer)
        → Repository saves into MySQL
        → saved Customer returns back
        → JSON response

        ✔ You can save multiple rows
        ✔ But save() usually saves one object (one row) at a time


        🔁 Multiple rows

        If you do this many times:
        save(customer1);
        save(customer2);
        save(customer3);

        👉 Database becomes:
        id | name
        1  | Temmy
        2  | John
        3  | Alice

        List<Customer> customers
        → saveAll(customers)

        save(customer) → INSERT 1 row
        saveAll(list) → INSERT multiple rows


         */

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    /*
    findAll() = get ALL rows from database

    returns: List<Customer>
    example: [Customer1, Customer2, Customer3...]

    Call database → return all rows

    🧠 Behind the scenes
    Spring runs SQL:
    SELECT * FROM customer;
     */

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
        // return Customer ORELSE return null(if Customer not found)
    }
    /*
    findById(id) → find ONE row using its ID

    example: id = 1
    customerRepository.findById(id)
    SELECT * FROM customer WHERE id = 1;
    returns Optional<Customer>
    because the customer may exist or may not exist.
    .orElse(null)
    if found → return Customer
    if not found → return null

    behind the scenes:
    Optional<Customer> optional = customerRepository.findById(id);

    if (optional.isPresent()) {
        return optional.get();
    } else {
        return null;
    }

    ✔ findById() returns Optional<Customer>
    ✔ .orElse(null) converts it to Customer
    ✔ That’s why your method returns Customer
     */

    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }
    }

    /*
    deleteById(id) → delete ONE row using its id

    Example table:
    customer
    ------------------------
    id | name   | email
    1  | Temmy  | t@mail.com
    2  | John   | j@mail.com
    3  | Alice  | a@mail.com

    customerRepository.deleteById(2L);
    Result:
    customer
    ------------------------
    id | name   | email
    1  | Temmy  | t@mail.com
    3  | Alice  | a@mail.com

    Spring runs:
    DELETE FROM customer WHERE id = 2;
     */

    public Customer updateCustomer(Long id, Customer newCustomer) {
        Customer existing = customerRepository.findById(id).orElse(null);

        // Find → Check → Update → Save
        if (existing == null) {
            return null;
        }
        // If not found → stop

        existing.setName(newCustomer.getName());
        existing.setEmail(newCustomer.getEmail());

        return customerRepository.save(existing);
        // Save → UPDATE happens
        // .save returns Customer
    }

    /*
    Update = modify existing row at that id

    Behind the scenes:

    UPDATE customer
    SET name = ?, email = ?
    WHERE id = ?;


    Full flow:
    PUT request
    → Controller receives id + new data
    → Service finds existing row
    → Service updates object using setters
    → save() called
    → Database row updated
     */

}
