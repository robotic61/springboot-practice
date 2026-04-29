package com.forthems.ftnlabel.repository;

import com.forthems.ftnlabel.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Right now we have table but:

We cannot put data into table yet

We need something that can do:

INSERT INTO customer ...
SELECT * FROM customer ...

That “something” = Repository
Repository = tool that talks to database

⚠️ Important difference
In normal Java:
Interface → I must write the code(method) myself

Spring boot:
You write interface
↓
Spring sees it
↓
Spring builds the real class for you
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContaining(String word); // Find name containing "mm" like "%mm%"

    List<Customer> findByNameStartingWith(String word);  // Find name starting with "word"

    List<Customer> findByNameEndingWith(String word); // Find name ending with "word"

    List<Customer> findByNameContainingAndEmailContaining(String name, String email);
    // Combine conditions
    // Find name containing "name" amd email containing "email".
}
    // JpaRepository<Entity, IdType>
    // this repository works with the Customer table and
    //  Customer's table id type is Long.

    //1. Which table? → Customer
    //2. What type is the id? → Long
    //JpaRepository<Customer, Long> = “Repository for Customer table using Long as id”

    /*
    Customer → which table
    Long → type of id


    Normal interface → YOU implement
    JpaRepository interface → SPRING implements

    Scans for repository interfaces
    Recognizes JpaRepository
    Generates implementation dynamically

    🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴
    JpaRepository = interface that declares database methods
    CustomerRepository = interface that inherits those methods
    Spring = creates the actual implementation automatically
    🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴

    Behind the scenes, Spring generates something like:

    class CustomerRepositoryImpl implements CustomerRepository {

        public Customer save(Customer c) {
            // Hibernate INSERT logic
        }

        public List<Customer> findAll() {
            // SELECT logic
        }

        public Optional<Customer> findById(Long id) {
            // SELECT WHERE id
        }
     }

    Spring boot implements ready-made database methods like:
    save()
    findAll()
    findById()
    deleteById()

    save(customer);          // insert or update
    findAll();               // get all rows
    findById(id);            // get one row
    deleteById(id);          // delete row
    count();                 // count rows

    JpaRepository (interface)
       ↓ declares methods
    CustomerRepository (interface)
       ↓ inherits methods
    Spring generates class
       ↓
    You use it in service
     */
